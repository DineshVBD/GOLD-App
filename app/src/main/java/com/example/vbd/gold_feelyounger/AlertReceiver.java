package com.example.vbd.gold_feelyounger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String text=intent.getStringExtra("text");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(text);
        notificationHelper.getManager().notify(1, nb.build());
        final MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);


        CountDownTimer countDownTimer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                mediaPlayer.start();
            }

            @Override
            public void onFinish() {
                mediaPlayer.stop();
            }
        };
        countDownTimer.start();
    }
}