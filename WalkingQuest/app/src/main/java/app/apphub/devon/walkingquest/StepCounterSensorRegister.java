package app.apphub.devon.walkingquest;

import android.app.Notification;
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
import android.util.Log;

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

                //Sets the activities messenger for sending the globalSteps back
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
                        messenger.send(Message.obtain(null, MSG_GET_SESSION_STEPS, (int) globalSteps, 0));
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
    //The number of globalSteps, increases as new step events are registered by the sensor
    long globalSteps;
    //Do we need these?
    boolean flag = false;
    boolean isRunning;

    @Override
    public void onCreate(){
        super.onCreate();
        isRunning = false;
        //globalSteps = 0;
        Log.i("CREATE", "STEP_COUNTER_CREATED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Must run in background thread
        if(!isRunning) {
            Log.i("START", "STEP_COUNTER_STARTED");
            //Get the sensor with a good delay (not too log not too high)
            //Returns whether or not the sesor has been registered by the service
            isRunning = registerSensor(sensorManager.SENSOR_DELAY_NORMAL);
            //Sets the steps to 0
            globalSteps = 0;
        }

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
        Log.i("CREATE", "STEP_COUNTER_DESTROYED");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        super.onTaskRemoved(rootIntent);

        //Save Steps to the database here once merged and database database is clearly defined to us

        Log.i("PLZ GOD", "ILL DO ANYTHING... ANYTHING");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //If the event registered is a step event increase the globalSteps
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            Log.i("STEPS DETECTED",""+globalSteps);
            globalSteps++;
            try {
                /*If the activities messenger is defined then send the step counter event to the activity
                * This will give real-time updates to an activity that can share messages between itself and the service*/
                if(messenger != null)
                    //Just return the number of global steps
                    //The 0 is a possible second argument
                    mMessenger.send(Message.obtain(null, StepCounterSensorRegister.MSG_GET_SESSION_STEPS, (int) globalSteps, 0));
            }catch (RemoteException e){
                Log.i("EXCEPTION","Message on sensor event failed");
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
            Log.i("ERROR", e.getMessage());
        }

        if(sensor == null) {
            Log.i("FAILED","Sensor returned null");
            return false;
        }
        else {
            Log.i("SUCCESS", "Successfully registered sensor");
            sensorManager.registerListener(this, sensor, accuracy);
            return true;
        }

    }

    public long getGlobalSteps() {
        return globalSteps;
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

}

