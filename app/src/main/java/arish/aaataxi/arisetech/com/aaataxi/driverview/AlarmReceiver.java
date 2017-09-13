package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Arish on 8/30/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent= new Intent(context, GPSService.class);
        context.startService(serviceIntent);
//        Toast.makeText(context,"I am running",Toast.LENGTH_SHORT).show();

    }
}
