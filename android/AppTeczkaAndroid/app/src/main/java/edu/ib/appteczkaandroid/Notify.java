package edu.ib.appteczkaandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

public class Notify extends BroadcastReceiver {

    String titleExtra = "Title";

    String messageExtra = "Message";
    int NOTIFICATION_ID = 100;




    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntentResult = PendingIntent.getActivity(
                context,
                getResultCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        System.out.println(intent.getStringExtra("titleExtra"));

        Notification builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.logotype)
                .setContentTitle(intent.getStringExtra("titleExtra"))
                .setContentText(intent.getStringExtra("messageExtra"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        manager.notify(NOTIFICATION_ID,builder);
        System.out.println(getResultCode());



    }
}
