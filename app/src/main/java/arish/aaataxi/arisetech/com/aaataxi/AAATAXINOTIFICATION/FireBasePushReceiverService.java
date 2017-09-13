package arish.aaataxi.arisetech.com.aaataxi.AAATAXINOTIFICATION;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import arish.aaataxi.arisetech.com.aaataxi.LoginActivity;
import arish.aaataxi.arisetech.com.aaataxi.R;


public class FireBasePushReceiverService extends FirebaseMessagingService {
    public static final String PUSH_NOTICATION = "pushNotification";
    public static final String PUSH_GROUND_NOTIFICATION = "psuhGroundNotification";
    public static final int PUSH_TYPE_USER = 1;

    //This method will call every new message received

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message = remoteMessage.getData().get("message");
        String title = remoteMessage.getData().get("title");
        adminNotification(message,title);
    }

//    public void onMessageReceived(String from, Bundle data) {
        //Getting the message from Bundle
//sharedPreferences = getBaseContext().getSharedPreferences(Session.SHARED_PREF_NAME,0);

//        if(sharedPreferences.getBoolean(Session.LOGGEDIN_SHARED_PREF,true)){
//            adminNotification(message,title);
//        }
//        if(sharedPreferences.getBoolean(Session.ADMIN_LOGGEDIN_SHARED_PREF,true)){
//        driverNotificationMessage(message,title);



//
//        }
//
//
    private void adminNotification(String message, String title) {
        Intent i = new Intent(getBaseContext(),LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        i.setClass(getBaseContext(),DriverMapsActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode = 0;

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder noBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getBaseContext())
                .setSmallIcon(R.drawable.ic_map_event)
//                .setContentTitle("NEW TAXI REQUEST")
//                .setContentText("Click To see the Customer Information")
//                .setTicker("New Notification From AAATAXINJ")
                .setAutoCancel(true)
                .setContentTitle("NEW COLOMBIATAXICAB REQUEST")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
             .setContentTitle(title)
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        noBuilder.setSound(sound);
        noBuilder.setFullScreenIntent(pendingIntent,true);
        if (Build.VERSION.SDK_INT >= 21) noBuilder.setVibrate(new long[]{1000,1000});
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ApplicationConstants of notification
       /* chatActivity = new ChatActivity();
        chatActivity.loadDummyHistory(message);*/
    }


}
