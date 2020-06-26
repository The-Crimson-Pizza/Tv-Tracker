package com.thecrimsonpizza.tvtracker.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.thecrimsonpizza.tvtracker.R;

import static com.thecrimsonpizza.tvtracker.util.Constants.GROUP_KEY_SEASON_NEW;
import static com.thecrimsonpizza.tvtracker.util.Constants.NEW_SEASON_NOTIFICATION_BUNDLE_CHANNEL_ID;
import static com.thecrimsonpizza.tvtracker.util.Constants.NEW_SEASON_NOTIFICATION_BUNDLE_CHANNEL_NAME;
import static com.thecrimsonpizza.tvtracker.util.Constants.NEW_SEASON_NOTIFICATION_CHANNEL_ID;
import static com.thecrimsonpizza.tvtracker.util.Constants.NEW_SEASON_NOTIFICATION_CHANNEL_NAME;
import static com.thecrimsonpizza.tvtracker.util.Constants.SEASON_ID_EXTRA;
import static com.thecrimsonpizza.tvtracker.util.Constants.SEASON_NUMBER_EXTRA;
import static com.thecrimsonpizza.tvtracker.util.Constants.SERIE_NOMBRE_EXTRA;

public class Receiver extends BroadcastReceiver {

    public static final String ACTION_ALARM_RECEIVER = "NewSeasonReceiver";
    private NotificationManager notificationManager;
    //    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder summaryNotificationBuilder;


    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra(SERIE_NOMBRE_EXTRA);
        int numTemporada = intent.getIntExtra(SEASON_NUMBER_EXTRA, 0);
        int id = intent.getIntExtra(SEASON_ID_EXTRA, 0);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager = NotificationManagerCompat.from(context);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            if (notificationManager.getNotificationChannels().size() < 2) {
            createBundleChannel();
            createNotificationChannel();
//            }
        }
        summaryNotificationBuilder = getSummaryNotificationBuilder(context);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, NEW_SEASON_NOTIFICATION_CHANNEL_ID)
                .setGroup(GROUP_KEY_SEASON_NEW)
                .setContentTitle("Nueva temporada")
                .setContentText(context.getString(R.string.notificacion_nueva_temporada, String.valueOf(numTemporada), name))
                .setSmallIcon(R.drawable.proto_logo)
                .setGroupSummary(false);

        notificationManager.notify(id, notification.build());
        notificationManager.notify(0, summaryNotificationBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NEW_SEASON_NOTIFICATION_CHANNEL_ID, NEW_SEASON_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createBundleChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel groupChannel = new NotificationChannel(NEW_SEASON_NOTIFICATION_BUNDLE_CHANNEL_ID, NEW_SEASON_NOTIFICATION_BUNDLE_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(groupChannel);
        }
    }

    private NotificationCompat.Builder getSummaryNotificationBuilder(Context context) {
        return new NotificationCompat.Builder(context, NEW_SEASON_NOTIFICATION_BUNDLE_CHANNEL_ID)
                .setGroup(GROUP_KEY_SEASON_NEW)
                .setGroupSummary(true)
                .setContentTitle("Nuevas temporadas")
                .setContentText("Aqui aparecen new seasons")
                .setSmallIcon(R.drawable.proto_logo);
    }

}
