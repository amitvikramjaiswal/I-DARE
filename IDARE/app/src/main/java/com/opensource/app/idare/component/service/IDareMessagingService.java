package com.opensource.app.idare.component.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.view.activity.SplashActivity;

import java.util.Map;
import java.util.Random;

import static android.app.Notification.BADGE_ICON_SMALL;

/**
 * Created by amitvikramjaiswal on 05/11/17.
 */

public class IDareMessagingService extends FirebaseMessagingService {

    private static final String TAG = IDareMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            showNotification(this, remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.    }
    }

    public void showNotification(Context context, Map<String, String> data) {
        Random random = new Random();
        int code = random.nextInt(1000);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String title = data.get("title");
        title = title.replace("|"," ");
        String body = data.get("body");
        body = body.replace("|", " ");

        Intent intent = new Intent(this, SplashActivity.class);
        intent.setAction(Constants.ACTION_NOTIFICATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, code, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Intent intent2 = new Intent(Constants.ACTION_NOTIFICATION_DISMISS);
        PendingIntent deleteIntent = PendingIntent.getBroadcast(context, code, intent2,
                PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = "I-DARE";
        CharSequence name = "I-DARE";
        String Description = "I-DARE";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(ContextCompat.getColor(context, android.R.color.transparent));
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentTitle(title)
                .setBadgeIconType(BADGE_ICON_SMALL) // only for Android 8 & above
                .setNumber(1) //this is incremental value  // only for Android 8 & above
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setDeleteIntent(deleteIntent);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        notificationManager.notify(code, notificationBuilder.build());
    }

}
