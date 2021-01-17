package com.example.advancedalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class RingtonePlayingService extends Service {

    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String state = intent.getStringExtra("extra");
        long alarmSongChoice = intent.getLongExtra("alarmSongChoice", 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intentMainActivity, 0);

        Notification notificationPopup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.bell)
                .setContentIntent(pendingIntentMainActivity)
                .setAutoCancel(true)
                .build();

        if (state.equals("alarm on")) {
            startId = 1;
        } else {
            startId = 0;
        }

        runAlarmSong(startId, alarmSongChoice, notificationManager, notificationPopup);

        return START_NOT_STICKY;
    }

    private void runAlarmSong(int startId, long alarmSongChoice, NotificationManager notificationManager, Notification notificationPopup) {
        if (!this.isMusicPlaying && startId == 1) {
            Log.e("there is no music,", "and you want start");

            notificationManager.notify(0, notificationPopup);

            this.isMusicPlaying = true;


            mediaPlayer = MediaPlayer.create(this, R.raw.creepyclockchiming);


            if (alarmSongChoice == 0) {

                mediaPlayer = MediaPlayer.create(this, R.raw.creepyclockchiming);

            } else if (alarmSongChoice == 1) {
                mediaPlayer = MediaPlayer.create(this, R.raw.creepyclockchiming);

            } else if (alarmSongChoice == 2) {
                mediaPlayer = MediaPlayer.create(this, R.raw.midnightchimessoundeffect);

            } else if(alarmSongChoice == 3){
                mediaPlayer = MediaPlayer.create(this, R.raw.timerclickingsound);
            }

            mediaPlayer.start();



        } else if (this.isMusicPlaying && startId == 0) {
            Log.e("there is music,", "and you want end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isMusicPlaying = false;


        } else if (!this.isMusicPlaying && startId == 0) {
            Log.e("there is no music,", "and you want end");

            this.isMusicPlaying = false;


        } else if (this.isMusicPlaying && startId == 1) {
            Log.e("there is  music,", "and you want start");

            this.isMusicPlaying = true;

        } else {
            Log.e("else", "somehow you reached this");

        }
    }

    @Override
    public void onDestroy() {
        Log.e("on Destroy called", "yeah");
    }
}
