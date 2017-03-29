package app.apphub.devon.walkingquest;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Quest;

/**
 * Created by Devon on 3/4/2017.
 *
 * Class that extends the Activity class. The purpose of this class is for any Activities that need access to the
 * total number of steps taken, ex: current quest screen
 *
 * @author Devon
 * @version 1.0
 * @since 2017-3-4
 *
 */

public class CustomActivity extends AppCompatActivity {


    //Service connector object for connecting the activity to the service
    private ServiceConnector mConnection = null;

    /**
     * Handler class for unpackaging an incoming message
     *
     * @author Devon
     * @version 1.0
     * @since 2017-3-4
     */

    public class IncomingHandler extends Handler {

        /**
         * The handleMessage function
         *
         * @author Devon
         * @version 1.0
         * @since 2017-3-4
         * @param msg This is the message object sent from the serivce
         */

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case StepCounterSensorRegister.MSG_GET_SESSION_STEPS:
                    Log.i("STEPS FROM SERVICE", ""+msg.arg1);
                    globalSteps = msg.arg1;
                    if(tv != null)
                        tv.setText(msg.arg1+"/"+quest.getStepGoal());
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

    }

    //The messenger object created from the IncomingHandler class defined above
    final Messenger mMessenger = new Messenger(new CustomActivity.IncomingHandler());

    /**
     * Bind the activity to the service to enable sending messages
     *
     * @author Devon
     * @version 1.0
     * @since 2017-3-4
     */
    void doBindService(){

        Log.i("PRE-SUCCESS", "Service Binding");

        if(mConnection == null)
            mConnection = new ServiceConnector(mMessenger);
        bindService(new Intent(CustomActivity.this, StepCounterSensorRegister.class), mConnection, Context.BIND_AUTO_CREATE);

    }

    /**
     * Unbind the activity from the service to disable sending messages
     *
     * @author Devon
     * @version 1.0
     * @since 2017-3-4
     */
    void doUnbindService() {
        if (mConnection != null) {
            if(mConnection.getMessenger() != null){
                try {
                    Message msg = Message.obtain(null, StepCounterSensorRegister.MSG_UNERGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mConnection.getMessenger().send(msg);
                }catch (RemoteException e){
                    Log.i("EXCEPTION", "Unbind exception " + e.getMessage());
                }
            }

            unbindService(mConnection);
            mConnection = null;
        }
    }

    /**
     * Sends a message from the client to the service asking the service to respond with the number of steps takes.
     * Cannot be called until the ServiceConnectors messenger has been created. That process happens asynchronously
     * if the number of steps is needed, say for UI, add a handler recursive handler that's base case is the value returned
     *
     * @author Devon
     * @version 1.0
     * @since 2017-3-4
     * @return boolean The status of service connection false for no service connection established
     */
    boolean getGlobalStepsFromService(){

        //If there is a connection then ask the service for the number of steps the sensor has registered
        if(mConnection != null){
            try{
                Message msg = Message.obtain(null, StepCounterSensorRegister.MSG_GET_SESSION_STEPS);
                msg.replyTo = mMessenger;
                mConnection.getMessenger().send(msg);
                return true;
            }catch(RemoteException e){
                Log.i("EXCEPTION", "Unable to get global steps " + e.getMessage());
            }
        }
        return false;
    }


    int globalSteps;
    Context context;
    Intent intent;
    TextView tv;

    //TODO:remove this
    DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getBaseContext());
    Quest quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Prepare to start the service
        context = getBaseContext();
        intent = new Intent(context, StepCounterSensorRegister.class);

        quest = databaseHandler.getQuestByID(1);

        /*Check if the service is running
        * on first boot of the app the service doesn't automatically start
        * also if the phone crashes the */
        if(!isMyServiceRunning(StepCounterSensorRegister.class)) {
            Log.i("SUCCESS", "FIRST TIME STARTING");
            startService(intent);
        }


        //Bind the step counter service
        doBindService();

    }


    @Override
    protected void onPause(){
        super.onPause();
        //Unbind the service messenger
        doUnbindService();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //If the service messenger is unbound bind it
        if(mConnection == null)
            doBindService();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //When the application is closed unbind the service messenger
        doUnbindService();
        //stopService(intent);
    }


    /**
     * Checks for a running running service
     *
     * @author Devon
     * @version 1.0
     * @since 2017-3-4
     * @param serviceClass the class that the service you wish to check is running
     * @return boolean whether or not a service of the class type supplied in the input is running
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("bitch", "better have my activity");
                return true;
            }
        }
        return false;
    }

}
