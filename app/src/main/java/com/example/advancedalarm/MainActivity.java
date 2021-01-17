package com.example.advancedalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent intent;
    private Calendar calendar;

    private TimePicker timePicker;
    private TextView updateText;
    long chooseAlarmClockSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = findViewById(R.id.timePicker);

        updateText = findViewById(R.id.update_text);

        calendar = Calendar.getInstance();

        Button alarmOn = findViewById(R.id.alarm_on);
        Button alarmOff = findViewById(R.id.alarm_off);

        intent = new Intent(this, AlarmReceiver.class);

        alarmOn.setOnClickListener(alarmOnListener());

        alarmOff.setOnClickListener(alarmOffListener());

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_elements, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    private void setAlarmText(String text) {
        updateText.setText(text);
    }

    private View.OnClickListener alarmOnListener() {
        View.OnClickListener alarmOnListener = view -> {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String hourString = String.valueOf(hour);
            String minuteString = String.valueOf(minute);
            String amOrPm = "AM";

            if (minute < 10) {
                minuteString = "0" + minuteString;
            }

            if (hour > 12) {
                hourString = String.valueOf(hour - 12);
                amOrPm = "PM";
            }

            MainActivity.this.setAlarmText("Alarm set to: " + hourString + ":" + minuteString + " " + amOrPm);

            intent.putExtra("extra", "alarm on");
            intent.putExtra("alarmSongChoice",chooseAlarmClockSong);



            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        };

        return alarmOnListener;

    }


    private View.OnClickListener alarmOffListener() {
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        View.OnClickListener alarmOffListener = view -> {
            setAlarmText("Alarm off!");
            alarmManager.cancel(pendingIntent);

            intent.putExtra("extra", "alarm off");
            intent.putExtra("alarmSongChoice",chooseAlarmClockSong);

            sendBroadcast(intent);


        };

        return alarmOffListener;

    }

    @SuppressLint("ShowToast")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(adapterView.getContext(),"THE ID FROM TOAST: " + l,Toast.LENGTH_SHORT).show();
        chooseAlarmClockSong = l;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}