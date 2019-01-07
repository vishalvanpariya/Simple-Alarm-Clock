package com.example.vishal.alarm_clock;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class alarm_run extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vishal.alarm_clock.R.layout.activity_alarm_run);

        int a = getIntent().getExtras().getInt("position");
        sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
        int temp = sharedPreferences.getInt("lastkeyH",0);
        ArrayList<Integer> arrayListhour = new ArrayList<Integer>();
        ArrayList<Integer> arrayListmin = new ArrayList<Integer>();
        ArrayList<Integer> arrayListflag = new ArrayList<Integer>();
        if (temp != 0){
            for (int i = 0; i < temp-1; i++) {
                int hh = sharedPreferences.getInt(String.valueOf(i+1), 0);
                arrayListhour.add(hh);
            }
            for (int j = 0; j < temp-1; j++) {
                int mm = sharedPreferences.getInt(String.valueOf(j+1+100), 0);
                arrayListmin.add(mm);
            }
            for (int k = 0; k < temp - 1; k++){
                int ff = sharedPreferences.getInt(String.valueOf(k+1+200), 0);
                arrayListflag.add(ff);
            }
        }
        arrayListflag.set(a,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        for (int i = 0;i<arrayListhour.size();i++){
            savealaram(arrayListhour.get(i),arrayListmin.get(i),arrayListflag.get(i));
        }
    }

    public void stop(View view){
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void SetSnooze(View view) {

    }

    public void savealaram(int hour,int minutes,int flag){
        sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int lastkeyH,lastkeyM,f;
        lastkeyH = sharedPreferences.getInt("lastkeyH",0);
        lastkeyM = sharedPreferences.getInt("lastkeyH",0);
        f = sharedPreferences.getInt("lastkeyH",0);
        lastkeyM = lastkeyM + 100;
        f = f + 200;
        if (lastkeyH == 0){
            lastkeyH++;
            lastkeyM++;
            f++;
        }
        editor.putInt(String.valueOf(lastkeyH), hour);
        editor.putInt(String.valueOf(lastkeyM), minutes);
        editor.putInt(String.valueOf(f),flag);
        lastkeyH++;
        lastkeyM++;
        f++;
        editor.putInt("lastkeyH",lastkeyH);
        editor.apply();
    }
}
