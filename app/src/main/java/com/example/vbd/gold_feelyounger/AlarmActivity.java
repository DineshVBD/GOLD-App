package com.example.vbd.gold_feelyounger;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;


public class AlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView breakfasttextview;
    private TextView lunchtextview;
    private TextView dinnertextview;
    private TextView exercisetextview;
    int finder=0;
    public String text = "";
    int firstreqcode=1;
    int secondreqcode=2;
    int thirdreqcode=3;
    int fourthreqcode=4;
    int alreadyset=0;
    Button breakfastsetbutton;
    Button breakfastcancelbutton;
    Button lunchsetbutton;
    Button lunchcancelbutton;
    Button dinnersetbutton;
    Button dinnercancelbutton;
    Button exercisesetbutton;
    Button exercisecancelbutton;
    SharedPreferences sharedPreferences;
    int[] cancelstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        breakfasttextview = findViewById(R.id.breakfasttextview);
        lunchtextview = findViewById(R.id.lunchtextview);
        dinnertextview = findViewById(R.id.dinnertextview);
        exercisetextview = findViewById(R.id.execisetextview);
        sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        cancelstatus=new int[4];


        if(!((sharedPreferences.getString("time1","")).equals(""))) {
            breakfasttextview.setText(sharedPreferences.getString("time1",""));
        }

        if(!((sharedPreferences.getString("time2","")).equals(""))) {
            lunchtextview.setText(sharedPreferences.getString("time2",""));
        }

        if(!((sharedPreferences.getString("time3","")).equals(""))) {
            dinnertextview.setText(sharedPreferences.getString("time3",""));
        }

        if(!((sharedPreferences.getString("time4","")).equals(""))) {
            exercisetextview.setText(sharedPreferences.getString("time4",""));
        }


        breakfastsetbutton=findViewById(R.id.breakfastsetbutton);
        breakfastsetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                finder=1;
                text ="It's time for Breakfast!!!";
            }
        });

        breakfastcancelbutton = findViewById(R.id.breakfastcancelbutton);
        breakfastcancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finder=1;
                cancelAlarm();
            }
        });

        lunchsetbutton= findViewById(R.id.lunchsetbuton);
        lunchsetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
               finder=2;
                text ="It's time for Lunch!!!";
            }
        });

        lunchcancelbutton= findViewById(R.id.lunchcancelbutton);
        lunchcancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finder=2;
                cancelAlarm();
            }
        });

        dinnersetbutton = findViewById(R.id.dinnersetbutton);
        dinnersetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                finder=3;
                text="It's time for Dinner!!!";
            }
        });

        dinnercancelbutton= findViewById(R.id.dinnercancelbutton);
        dinnercancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finder=3;
                cancelAlarm();
            }
        });

        exercisesetbutton= findViewById(R.id.exercisesetbutton);
        exercisesetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                finder=4;
                text="It's time for some Exercise!!!";
            }
        });

        exercisecancelbutton = findViewById(R.id.exercisecancelbutton);
        exercisecancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finder=4;
                cancelAlarm();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c,text);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c,String text) {
        String timeText = "Alarm set: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        if(finder==1) {
                breakfasttextview.setText(timeText);
                sharedPreferences.edit().putString("time1", timeText).apply();

        }
        else if(finder==2) {

                lunchtextview.setText(timeText);
                sharedPreferences.edit().putString("time2", timeText).apply();

        }
        else if(finder==3) {

                dinnertextview.setText(timeText);
                sharedPreferences.edit().putString("time3", timeText).apply();

        }
        else {

                exercisetextview.setText(timeText);
                sharedPreferences.edit().putString("time4", timeText).apply();
        }
        }

    private void startAlarm(Calendar c) {

        if(finder==1) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtra("text", text);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE,1);
            }


            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, firstreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
          //  cancelstatus[0]=i;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,pendingIntent);

        }

        if(finder==2) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtra("text", text);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE,1);
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, secondreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         //   cancelstatus[1]=i;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,pendingIntent);

        }

        if(finder==3) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtra("text", text);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE,1);
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, thirdreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
          //  cancelstatus[2]=i;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,pendingIntent);

        }

        if(finder==4) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtra("text", text);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE,1);
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, fourthreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
          //  cancelstatus[3]=i;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,pendingIntent);

        }


       }

    private void cancelAlarm() {


        if(finder==1) {
            breakfasttextview.setText("        OFF");
            sharedPreferences.edit().putString("time1", "").apply();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, firstreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
        }

        if(finder==2) {
            lunchtextview.setText("        OFF");
            sharedPreferences.edit().putString("time2", "").apply();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, secondreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);

        }
        if(finder==3) {
            dinnertextview.setText("        OFF");
            sharedPreferences.edit().putString("time3", "").apply();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, thirdreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
        }
        if(finder==4) {
            exercisetextview.setText("        OFF");
            sharedPreferences.edit().putString("time4", "").apply();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, fourthreqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
        }

    }
}