package app.apphub.devon.walkingquest;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by adria on 2017-03-03.
 *
 * ServiceConnector is used by Activities to send messages to the any serivce
 *
 * @author Devon
 * @version 1.0
 * @since 2017-3-4
 *
 */

public class ServiceConnector implements ServiceConnection {

    //mService the service messenger that the activity will be sending a message to
    //mMessenger the handler that the service receiving the message will be expected to send a reply to
    Messenger mService, mMessenger;
    //Do we need this anymore?
    private static boolean serviceStarted = false;

    /**
     * The handleMessage function
     *
     * @author Adrian
     * @version 1.0
     * @since 2017-3-4
     * @param mMessenger This is the Messenger&IncomingHandler that the service will reply to
     */

    public ServiceConnector(Messenger mMessenger){
        this.mMessenger = mMessenger;
    }


    /*This method needs to be implemented refer to ServiceConnection for definition
    * Callback type method*/
    public void onServiceConnected(ComponentName className, IBinder service){

        //Set the messenger that the activity will be sending messages to
        mService = new Messenger(service);

        try{
            //The construction of the message
            Message msg = Message.obtain(null, StepCounterSensorRegister.MSG_REGISTER_CLIENT);
            //Defining the sender
            msg.replyTo = mMessenger;
            //Sending the message to the services messenger
            mService.send(msg);
            serviceStarted = true;
            Log.i("SUCCESS", "Service Bind");
        }
        catch (RemoteException e){
            Log.i("EXCEPTION", "Service connection exception " + e.getMessage());
        }
    }

    /*This method needs to be implemented refer to ServiceConnection for definition
    * Callback type method*/
    public void onServiceDisconnected(ComponentName className){
        mService = null;
        serviceStarted = false;
    }


    /*

    public void getServiceGlobalSteps(ComponentName className, IBinder service){
        if(mService!=null){
            try{
                Message msg = Message.obtain(null, StepCounterSensorRegister.MSG_GET_SESSION_STEPS);
                msg.replyTo = mMessenger;
                mService.send(msg);
                Log.i("SUCCESS", "Service Getting Steps");
            }
            catch (RemoteException e){
                Log.i("EXCEPTION", "Service connection exception " + e.getMessage());
            }
        }else{
            Log.i("SERVICE CONNECTION", "mService is null");
        }
    }
    */

    public Messenger getMessenger(){ return mService; }
    public static boolean isServiceStarted(){ return serviceStarted; };

}
