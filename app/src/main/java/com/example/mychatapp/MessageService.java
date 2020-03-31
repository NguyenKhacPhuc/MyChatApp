package com.example.mychatapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.Random;

public class MessageService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notification_channel_id = "message_app_testing";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
        NotificationChannel notificationChannel = new NotificationChannel(notification_channel_id,
                "notification",NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("Message Incoming");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setVibrationPattern(new long[] {0,1000,500,1000});
        notificationChannel.enableVibration(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notifcationBuilder = new NotificationCompat.Builder(this,notification_channel_id);
        notifcationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("In4");
        assert notificationManager != null;
        notificationManager.notify(new Random().nextInt(),notifcationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("TokenFirebase",s);
    }
}
