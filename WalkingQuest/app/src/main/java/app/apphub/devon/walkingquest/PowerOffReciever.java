package app.apphub.devon.walkingquest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/**
 * Created by Devon on 3/28/2017.
 */

public class PowerOffReciever extends BroadcastReceiver
{

    /*
    Purpose is to save the number of steps in the StepCounterSensorRegister service on a poweroff event

    author: Devon Rimmington
    since: Mar 28 2017
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        StepCounterSensorRegister.forceSaveSteps();
    }
}
