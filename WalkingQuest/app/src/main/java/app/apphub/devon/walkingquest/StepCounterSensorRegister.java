package app.apphub.devon.walkingquest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import app.apphub.devon.walkingquest.Helper.StringUtils;
import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Quest;

/**
 * Created by adria on 2017-02-23.
 */

public class StepCounterSensorRegister extends Service implements SensorEventListener {

    //Messenger for sending reply to the activity
    Messenger messenger = null;

    /*
    * Command for registering the activity messenger
    * */
    static final int MSG_REGISTER_CLIENT = 1;

    /*
    * Command for unregistering a activity messenger
    * */
    static final int MSG_UNERGISTER_CLIENT = 2;

    /*
    * Command for getting the number of steps recorded while the service is active
    * */
    static final int MSG_GET_SESSION_STEPS = 3;

    /*
    * Adds a new quest to the character object
    **/
    static final int MSG_ADD_NEW_QUEST = 4;

    /*
    * Get the character object again from the database
    * To be used if significant changes are made to the character
    **/
    static final int MSG_UPDATE_CHARACTER = 5;


    /**
    * Implementing the Message Handler. Used for when the activity sends a message to the service (ex: getting the session steps)
     *
     * @author Adrian
     * @version 1.0
     * @since 2017-3-4
    */


    class IncomingHandler extends Handler{

        @Override
        public void handleMessage(Message msg){

            Log.i("SUCCESS", "Service what? " + msg.what);

            switch (msg.what){

                //Sets the activities messenger for sending the activeSteps back
                case StepCounterSensorRegister.MSG_REGISTER_CLIENT:
                    messenger = msg.replyTo;
                    Log.i("SERVICE", "Registered");
                    break;
                //Sets the activities messenger to null and allow for another to be connected
                case StepCounterSensorRegister.MSG_UNERGISTER_CLIENT:
                    messenger = null;
                    Log.i("SERVICE", "Unregistered");
                    break;
                case StepCounterSensorRegister.MSG_GET_SESSION_STEPS:
                    //Send a message back to the activity contining the number of steps recorded during the session
                    try {
                        messenger.send(Message.obtain(null, MSG_GET_SESSION_STEPS, (int) activeSteps, 0));
                        Log.i("SERVICE", "Returning Steps");
                    }catch (RemoteException e){
                        Log.i("EXCEPTION", "Incoming handler exception " + e.getMessage());
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }

    //Messenger for handling incoming messages
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    //SensorManager for retrieving the step counter sensor
    SensorManager sensorManager;
    //Seneor for holding onto the sensor after finding it through the SensorManager
    Sensor sensor;
    //The number of activeSteps, increases as new step events are registered by the sensor
    private static long activeSteps, stepGoal;
    //Do we need these?
    boolean flag = false;
    boolean isRunning;

    private static DatabaseHandler databaseHandler = null;
    private static Character character = null;
    private static Quest quest = null;

    //Strings for the quest completed notification
    private String notificationTitle = "Quest Completed",
        notificationDescription = "Your hero has completed ";

    @Override
    public void onCreate(){
        super.onCreate();
        isRunning = false;

        //check if first time loading
        if(quest == null)
            initCharacter();

        //Log.i("CREATE", "STEP_COUNTER_CREATED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Must run in background thread
        if(!isRunning) {

            if(quest == null)
                initCharacter();


            Log.i("SERVICE", "STEP_COUNTER_STARTED");
            //Get the sensor with a good delay (not too log not too high)
            //Returns whether or not the sesor has been registered by the service
            isRunning = registerSensor(sensorManager.SENSOR_DELAY_NORMAL);
        }

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        saveSteps();

        unregister();
        Log.i("SERVICE", "service destroyed steps save");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        super.onTaskRemoved(rootIntent);

        saveSteps();

        Log.i("SERVICE", "task removed steps save");
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        saveSteps();
        Log.i("SERVICE", "low memory steps save");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //If the event registered is a step event increase the activeSteps
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER && quest != null){
            activeSteps++;

            Log.i("SERVICE", "global steps: " + activeSteps + " steps for quest: " + stepGoal + " quest completed " + quest.isCompleted());

            if(activeSteps % 10 == 0){
                saveSteps();
            }

            if(activeSteps >= stepGoal && !quest.isCompleted()){

                //build and summon the notification
                Notification repliedNotification =
                        new Notification.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.temp_image)
                                .setContentTitle(notificationTitle)
                                .setContentText(notificationDescription + quest.getName())
                                .build();

                //initalizes the notification manager and posts the notification to the user
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                notificationManager.notify(0, repliedNotification);
                Log.i("Notification", "Yo");

                //set the quest information and save it to the database
                endQuest();

            }

            try {
                /*If the activities messenger is defined then send the step counter event to the activity
                * This will give real-time updates to an activity that can share messages between itself and the service*/
                if(messenger != null)
                    //Just return the number of global steps
                    //The 0 is a possible second argument
                    mMessenger.send(Message.obtain(null, StepCounterSensorRegister.MSG_GET_SESSION_STEPS, (int) activeSteps, 0));
            }catch (RemoteException e){
                Log.i("SERVICE","Message on sensor event failed");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * The handleMessage function
     *
     * @author Adrian
     * @version 1.0
     * @since 2017-3-4
     * @param accuracy One of the predefined (final) values from the SensorManager
     * @return boolean Whether or not the sensor could be registered
     */

    public boolean registerSensor(int accuracy) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        try {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }catch (Exception e){
            Log.i("SERVICE", e.getMessage());
        }

        if(sensor == null) {
            Log.i("SERVICE", "Failed to set the step sensor");
            return false;
        }
        else {
            Log.i("SERVICE", "Successfully registered sensor");
            sensorManager.registerListener(this, sensor, accuracy);
            return true;
        }

    }

    public long getactiveSteps() {
        return activeSteps;
    }

    public boolean isRegistered(){
        return sensor == null;
    }

    public Sensor getSensor() {
        return sensor;
    }

    /**
     * Unregisters the sensor from the service
     *
     * @author Adrian
     * @version 1.0
     * @since 2017-3-4
     */
    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    /*
    * Initalizes the character and the quest objects within the service
    *
    *
    * Author: Devon Rimmington
    **/

    private void initCharacter(){
        databaseHandler = DatabaseHandler.getInstance(getBaseContext());
        character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

        int questID = character.getCurrentQuestId();
        quest = databaseHandler.getQuestByID(questID);
        if(quest != null){
            activeSteps = quest.getActiveSteps();
            stepGoal = quest.getStepGoal();
        }else{
            quest = null;
            Log.i("SERVICE", "Failed to get quest information " + questID);
        }
    }


    /*
    * Enables emergency quick save of the users steps
    * Intended to be used when the device itself is restarted or turned off
    *
    * Author: Devon Rimmington
    **/
    public static void forceSaveSteps(){
        if(databaseHandler != null) {
            if (character != null) {
                //Save Steps to the database here once merged and database database is clearly defined to us
                if (quest != null) {
                    quest.setActiveSteps(activeSteps);
                    databaseHandler.updateQuest(quest);
                }
            }
        }
    }

    /*
    * Saves and ends the quest data from within the service
    *
    * Author: Devon Rimmington
    **/
    private void endQuest(){
        if(quest != null){
            quest.setCompleted(true);
            quest.setActiveSteps(activeSteps);
            character.setRewardIds(StringUtils.addCompletedQuestToCharacter(character.getRewardIds(), quest.getId()));
            character.setCurrentQuestId(0);
            databaseHandler.updateQuest(quest);
            databaseHandler.updateCharacter(character);
            quest = null;
            activeSteps = 0;
        }
    }

    /*
    * Saves the quest's number of steps
    *
    * Author: Devon Rimmington
    **/

    private void saveSteps(){
        //Save Steps to the database here once merged and database database is clearly defined to us
        if(quest != null){
            quest.setActiveSteps(activeSteps);
            databaseHandler.updateQuest(quest);
        }
    }

    /*
    * Forces the service to reload the character and the quest
    * To be used when the quest has been changed or added
    *
    * Author: Devon Rimmington
    **/

    public static void characterAltered(){

        if(databaseHandler != null) {
            character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

            quest = databaseHandler.getQuestByID(character.getCurrentQuestId());

            if(quest != null){
                activeSteps = 0;
                stepGoal = quest.getStepGoal();
                Log.i("SERVICE", "quest changed and selected " + quest.getId());
            }

        }

    }
}

