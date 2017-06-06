package pv239.brnorentalsapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;

/**
 * Created by admin on 05.06.2017.
 */

public class Notifications {
    private static Timer mTimer;

    public static void cancelNotifications(){
        if (mTimer != null)
            mTimer.cancel();
    }

    public static void setTimer(Timer timer){
        mTimer = timer;
    }

    public static Timer getTimer(){
        return mTimer;
    }

    public static void generateNotification(Context context, String message) {

        int icon = R.mipmap.ic_notification_white;
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);

        long[] pattern = {300,300,300,300};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification = builder.setContentIntent(contentIntent)
                .setSmallIcon(icon)
                .setTicker(appname)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(appname)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(pattern)
                .setContentText(message)
                .setSound(alarmSound)
                .build();

        notificationManager.notify((int) when, notification);


    }
}
