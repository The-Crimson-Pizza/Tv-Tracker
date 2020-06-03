package com.thecrimsonpizza.tvtracker.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.thecrimsonpizza.tvtracker.R;

import static com.thecrimsonpizza.tvtracker.util.Constants.NEW_SEASON_NOTIFICATION_CHANNEL;
import static com.thecrimsonpizza.tvtracker.util.Constants.SEASON_NUMBER_EXTRA;
import static com.thecrimsonpizza.tvtracker.util.Constants.SERIE_NOMBRE_EXTRA;

public class Receiver extends BroadcastReceiver {

    public static final String ACTION_ALARM_RECEIVER = "NewSeasonReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent(context, NewSeasonNotifications.class);
//        context.startService(intent1);

        int id = 1;
        String name = intent.getStringExtra(SERIE_NOMBRE_EXTRA);
        int numTemporada = intent.getIntExtra(SEASON_NUMBER_EXTRA, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NEW_SEASON_NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.proto_logo)
                .setContentTitle("Nueva temporada")
                .setContentText(context.getString(R.string.notificacion_nueva_temporada, String.valueOf(numTemporada), name))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, builder.build());
    }
}
