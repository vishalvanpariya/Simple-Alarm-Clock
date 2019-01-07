package com.example.vishal.alarm_clock;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class new_alarm extends AppCompatActivity {

    TimePicker timePicker;
    Button button;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vishal.alarm_clock.R.layout.activity_new_alarm);
        Toolbar toolbar = (Toolbar) findViewById(com.example.vishal.alarm_clock.R.id.toolbar);
        setSupportActionBar(toolbar);

        timePicker = (TimePicker)findViewById(com.example.vishal.alarm_clock.R.id.timePicker);
        button = (Button)findViewById(com.example.vishal.alarm_clock.R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = String.valueOf(timePicker.getCurrentHour());
                String minutes = String.valueOf(timePicker.getCurrentMinute());
                Intent intent = new Intent();
                intent.putExtra("hour", hour);
                intent.putExtra("minutes", minutes);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
