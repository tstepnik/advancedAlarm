package com.example.advancedalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {



        String extra = intent.getStringExtra("extra");

        long alarmSongId = intent.getExtras().getLong("alarmSongChoice");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra",extra);
        service_intent.putExtra("alarmSongChoice",alarmSongId);
        context.startService(service_intent);

    }
}
