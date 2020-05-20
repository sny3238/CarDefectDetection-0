package com.example.carcarcarcar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class YOLOService extends Service {

    private static final String CHANNEL_ID = "MyNotificationChannelID";
    public YOLOService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean message = intent.getBooleanExtra("YOLO_done", true);


        Intent notificationIntent = new Intent(this, AfterPastHistory.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, 0);


        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("YOCO : You Only Check Once")
                .setContentText("결함 비교분석 중...")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.btn_star)
                .build();

        Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("YOCO : You Only Check Once")
                .setContentText("분석이 완료되었습니다!")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.btn_star)
                .build();

        //채널등록
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My YOLO alert Service", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        if(!message){ // false
            startForeground(1, notification1);
        } else {    // true
            stopForeground(true);
            startForeground(1, notification2);
        }


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}