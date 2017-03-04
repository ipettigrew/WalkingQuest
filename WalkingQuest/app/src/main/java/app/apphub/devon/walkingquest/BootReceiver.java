package app.apphub.devon.walkingquest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by adria on 2017-02-24.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //If the broadcast received is a BOOT_ACTION_COMPLETED then start the StepCounterSensorRegister service
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.i("BROADCAST", "WALKING_QUEST_BOOT");
            Intent _intent = new Intent(context, StepCounterSensorRegister.class);
            context.startService(_intent);
        }
        else {
            Log.i("BROADCAST","NOT BOOT BROADCAST");
        }
    }
}
