package com.g9.letsmoveapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.app.NotificationCompat;

class NotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, NewRidesFragment.class);
        //Intent repeating_intent = new Intent(context, Repeating_activity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
/*
       NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Notificación título")
                .setContentText("Notificación Let's moveApp")
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());
*/
        /*
        NotificationCompat.Builder builder = (new NotificationCompat.Builder(context))
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Notificación título")
                .setContentText("Notificación Let's moveApp")
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());
        */



    }
}
