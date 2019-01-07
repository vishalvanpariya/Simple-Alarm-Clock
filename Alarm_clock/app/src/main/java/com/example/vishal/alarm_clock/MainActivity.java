package com.example.vishal.alarm_clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    int a =1;
    RecyclerView recyclerView;
    MyOwnAdapter ad;
    SharedPreferences sharedPreferences,s;
    AlarmManager[] alarmManager = new AlarmManager[10000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vishal.alarm_clock.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.example.vishal.alarm_clock.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.example.vishal.alarm_clock.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,new_alarm.class);
                startActivityForResult(intent,a);
            };
        });


        recyclerView =(RecyclerView)findViewById(com.example.vishal.alarm_clock.R.id.Recycler);
        sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
        int t;
        ArrayList<Integer> arrayListhour = new ArrayList<Integer>();
        ArrayList<Integer> arrayListmin = new ArrayList<Integer>();
        ArrayList<Integer> arrayListflag = new ArrayList<Integer>();
        t = sharedPreferences.getInt("lastkeyH",0);
        if (t != 0){
            for (int i = 0; i < t-1; i++) {
                int hh = sharedPreferences.getInt(String.valueOf(i+1), 0);
                arrayListhour.add(hh);
            }
            for (int j = 0; j < t-1; j++) {
                int mm = sharedPreferences.getInt(String.valueOf(j+1+100), 0);
                arrayListmin.add(mm);
            }
            for (int k = 0; k < t - 1; k++){
                int ff = sharedPreferences.getInt(String.valueOf(k+1+200), 0);
                arrayListflag.add(ff);
            }
            ad = new MyOwnAdapter(this,arrayListhour,arrayListmin,arrayListflag);
            ad.setCallback(new MyOwnAdapter.Call() {
                @Override
                public void onCheckedChanged(int item, boolean isChecked) {
                    if (isChecked){
                        sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                        ArrayList<Integer> listhour = new ArrayList<Integer>();
                        ArrayList<Integer> listminut = new ArrayList<Integer>();
                        ArrayList<Integer> listflag = new ArrayList<Integer>();
                        int temp = sharedPreferences.getInt("lastkeyH",0);
                        for (int i=0;i< temp-1;i++){
                            listhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
                        }
                        for (int j = 0; j < temp-1; j++){
                            listminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
                        }
                        for (int k = 0;k < temp-1;k++){
                            listflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                        }

                        for (int i = 0;i<listhour.size();i++) {
                            if (listflag.get(i) == 1) {
                                Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                                alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager[i].cancel(pi);
                            }
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        listflag.set(item,1);
                        for (int i = 0;i<listflag.size();i++){
                            savealaram(listhour.get(i),listminut.get(i),listflag.get(i));
                        }
                        ArrayList<Long> array = new ArrayList<Long>();
                        for(int i = 0,j = 0;i<temp-1;i++,j++){
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(calendar.get(calendar.YEAR),
                                    calendar.get(calendar.MONTH),
                                    calendar.get(calendar.DAY_OF_MONTH),
                                    sharedPreferences.getInt(String.valueOf(i+1), 0),
                                    sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                    0);
                            long timeInMillis = calendar.getTimeInMillis();
                            array.add(timeInMillis);
                        }
                        setalaram(array,listflag);
                    }
                    if (!isChecked){
                        sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                        ArrayList<Integer> listhour = new ArrayList<Integer>();
                        ArrayList<Integer> listminut = new ArrayList<Integer>();
                        ArrayList<Integer> listflag = new ArrayList<Integer>();
                        int temp = sharedPreferences.getInt("lastkeyH",0);
                        for (int i=0;i< temp-1;i++){
                            listhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
                        }
                        for (int j = 0; j < temp-1; j++){
                            listminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
                        }
                        for (int k = 0;k < temp-1;k++){
                            listflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                        }

                        for (int i = 0;i<listhour.size();i++) {
                            if (listflag.get(i) == 1) {
                                Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                                alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager[i].cancel(pi);
                            }
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        listflag.set(item,0);
                        for (int i = 0;i<listflag.size();i++){
                            savealaram(listhour.get(i),listminut.get(i),listflag.get(i));
                        }
                        ArrayList<Long> array = new ArrayList<Long>();
                        for(int i = 0,j = 0;i<temp-1;i++,j++){
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(calendar.get(calendar.YEAR),
                                    calendar.get(calendar.MONTH),
                                    calendar.get(calendar.DAY_OF_MONTH),
                                    sharedPreferences.getInt(String.valueOf(i+1), 0),
                                    sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                    0);
                            long timeInMillis = calendar.getTimeInMillis();
                            array.add(timeInMillis);
                        }
                        setalaram(array,listflag);

                    }
                }
            });
            recyclerView.setAdapter(ad);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == a && resultCode == RESULT_OK && data != null ){
            int h,m;
            h = Integer.parseInt(data.getStringExtra("hour"));
            m = Integer.parseInt(data.getStringExtra("minutes"));
            savealaram(h,m,1);
            /*Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH),h,m,0);
            setalaram(calendar.getTimeInMillis());*/
            Toast.makeText(this,"alaram is set",Toast.LENGTH_SHORT).show();
            int temp;
            sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
            temp = sharedPreferences.getInt("lastkeyH",0);
            ArrayList<Long> array = new ArrayList<Long>();
            ArrayList<Integer> fla = new ArrayList<Integer>();
            if (temp!=0){
                for(int i = 0,j = 0;i<temp-1;i++,j++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(calendar.get(calendar.YEAR),
                            calendar.get(calendar.MONTH),
                            calendar.get(calendar.DAY_OF_MONTH),
                            sharedPreferences.getInt(String.valueOf(i+1), 0),
                            sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                            0);
                    long timeInMillis = calendar.getTimeInMillis();
                    array.add(timeInMillis);
                }
                for (int k = 0;k<temp-1;k++){
                    fla.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                }
                setalaram(array,fla);
            }
            recyclerView =(RecyclerView)findViewById(com.example.vishal.alarm_clock.R.id.Recycler);
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
                ad = new MyOwnAdapter(this,arrayListhour,arrayListmin,arrayListflag);
                ad.setCallback(new MyOwnAdapter.Call() {
                    @Override
                    public void onCheckedChanged(int item, boolean isChecked) {
                        if (isChecked){
                            sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                            ArrayList<Integer> listhour = new ArrayList<Integer>();
                            ArrayList<Integer> listminut = new ArrayList<Integer>();
                            ArrayList<Integer> listflag = new ArrayList<Integer>();
                            int temp = sharedPreferences.getInt("lastkeyH",0);
                            for (int i=0;i< temp-1;i++){
                                listhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
                            }
                            for (int j = 0; j < temp-1; j++){
                                listminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
                            }
                            for (int k = 0;k < temp-1;k++){
                                listflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                            }

                            for (int jk = 0;alarmManager[jk]!=null;jk++){
                                if (listflag.get(jk) == 1) {
                                    Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                                    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, jk, intent, 0);

                                    alarmManager[jk] = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    alarmManager[jk].cancel(pi);
                                }
                            }
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            listflag.set(item,1);
                            for (int i = 0;i<listflag.size();i++){
                                savealaram(listhour.get(i),listminut.get(i),listflag.get(i));
                            }
                            ArrayList<Long> array = new ArrayList<Long>();
                            for(int i = 0,j = 0;i<temp-1;i++,j++){
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(calendar.get(calendar.YEAR),
                                        calendar.get(calendar.MONTH),
                                        calendar.get(calendar.DAY_OF_MONTH),
                                        sharedPreferences.getInt(String.valueOf(i+1), 0),
                                        sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                        0);
                                long timeInMillis = calendar.getTimeInMillis();
                                array.add(timeInMillis);
                            }
                            setalaram(array,listflag);
                        }
                        if (!isChecked){
                            sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                            ArrayList<Integer> listhour = new ArrayList<Integer>();
                            ArrayList<Integer> listminut = new ArrayList<Integer>();
                            ArrayList<Integer> listflag = new ArrayList<Integer>();
                            int temp = sharedPreferences.getInt("lastkeyH",0);
                            for (int i=0;i< temp-1;i++){
                                listhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
                            }
                            for (int j = 0; j < temp-1; j++){
                                listminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
                            }
                            for (int k = 0;k < temp-1;k++){
                                listflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                            }

                            for (int i = 0;i<listhour.size();i++) {
                                if (listflag.get(i) == 1) {
                                    Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                                    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                                    alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    alarmManager[i].cancel(pi);
                                }
                            }
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            listflag.set(item,0);
                            for (int i = 0;i<listflag.size();i++){
                                savealaram(listhour.get(i),listminut.get(i),listflag.get(i));
                            }
                            ArrayList<Long> array = new ArrayList<Long>();
                            for(int i = 0,j = 0;i<temp-1;i++,j++){
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(calendar.get(calendar.YEAR),
                                        calendar.get(calendar.MONTH),
                                        calendar.get(calendar.DAY_OF_MONTH),
                                        sharedPreferences.getInt(String.valueOf(i+1), 0),
                                        sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                        0);
                                long timeInMillis = calendar.getTimeInMillis();
                                array.add(timeInMillis);
                            }
                            setalaram(array,listflag);

                        }
                    }
                });
                recyclerView.setAdapter(ad);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 123 && resultCode == 456 && data != null){
            sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
            LinkedList<Integer> linkedListhour = new LinkedList<Integer>();
            LinkedList<Integer> linkedListminut = new LinkedList<Integer>();
            final LinkedList<Integer> linkedListflag = new LinkedList<Integer>();
            int temp = sharedPreferences.getInt("lastkeyH",0);
            for (int i=0;i< temp-1;i++){
                linkedListhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
            }
            for (int j = 0; j < temp-1; j++){
                linkedListminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
            }
            for (int k = 0;k < temp-1;k++){
                linkedListflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
            }
            for (int i = 0;i<linkedListhour.size();i++) {
                if (linkedListflag.get(i) == 1) {
                    Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                    Log.w("abc", String.valueOf(i));
                    alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager[i].cancel(pi);
                    Log.w("xyz", String.valueOf(i));
                }
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            String s = data.getStringExtra("delete");
            LinkedList<Integer> l = new LinkedList<Integer>();
            for (int i = 0;i<s.length();i++){
                l.add(Integer.parseInt(String.valueOf(s.charAt(i))));
            }

            for (int i=0;i<l.size();i++){
                for(int j=i;j<l.size();j++){
                    if (l.get(j)>l.get(i)){
                        int t = l.get(i);
                        int p = l.get(j);
                        l.remove(j);
                        l.remove(i);
                        l.add(i,p);
                        l.add(j,t);
                    }
                }
            }
            for(int i = 0;i<l.size();i++){
                linkedListhour.remove(l.get(i)-1);
                linkedListminut.remove(l.get(i)-1);
                linkedListflag.remove(l.get(i)-1);
            }


            if (!linkedListhour.isEmpty()) {
                for (int i = 0; i < linkedListhour.size(); i++) {
                    savealaram(linkedListhour.get(i), linkedListminut.get(i),linkedListflag.get(i));
                }
                int t = sharedPreferences.getInt("lastkeyH", 0);
                recyclerView = (RecyclerView) findViewById(com.example.vishal.alarm_clock.R.id.Recycler);
                ArrayList<Integer> arrayListhour = new ArrayList<Integer>();
                ArrayList<Integer> arrayListmin = new ArrayList<Integer>();
                ArrayList<Integer> arrayListflag = new ArrayList<Integer>();
                if (t != 0) {
                    for (int i = 0; i < t - 1; i++) {
                        int hh = sharedPreferences.getInt(String.valueOf(i + 1), 0);
                        arrayListhour.add(hh);
                    }
                    for (int j = 0; j < t - 1; j++) {
                        int mm = sharedPreferences.getInt(String.valueOf(j + 1 + 100), 0);
                        arrayListmin.add(mm);
                    }
                    for (int k = 0; k < t - 1; k++) {
                        int ff = sharedPreferences.getInt(String.valueOf(k + 1 + 200), 0);
                        arrayListflag.add(ff);
                    }
                    ad = new MyOwnAdapter(this,arrayListhour,arrayListmin,arrayListflag);
                    ad.setCallback(new MyOwnAdapter.Call() {
                        @Override
                        public void onCheckedChanged(int item, boolean isChecked) {
                            if (isChecked){
                                sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                                ArrayList<Integer> listhour = new ArrayList<Integer>();
                                ArrayList<Integer> listminut = new ArrayList<Integer>();
                                ArrayList<Integer> listflag = new ArrayList<Integer>();
                                int temp = sharedPreferences.getInt("lastkeyH",0);
                                for (int i=0;i< temp-1;i++){
                                    listhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
                                }
                                for (int j = 0; j < temp-1; j++){
                                    listminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
                                }
                                for (int k = 0;k < temp-1;k++){
                                    listflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                                }
                                for (int i = 0;i<listhour.size();i++) {
                                    if (listflag.get(i) == 1) {
                                        Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                                        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                                        alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        alarmManager[i].cancel(pi);
                                    }
                                }
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                listflag.set(item,1);

                                for (int i = 0;i<listflag.size();i++){
                                    savealaram(listhour.get(i),listminut.get(i),listflag.get(i));
                                }
                                ArrayList<Long> array = new ArrayList<Long>();
                                for(int i = 0,j = 0;i<temp-1;i++,j++){
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(calendar.get(calendar.YEAR),
                                            calendar.get(calendar.MONTH),
                                            calendar.get(calendar.DAY_OF_MONTH),
                                            sharedPreferences.getInt(String.valueOf(i+1), 0),
                                            sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                            0);
                                    long timeInMillis = calendar.getTimeInMillis();
                                    array.add(timeInMillis);
                                }
                                setalaram(array,listflag);
                            }
                            if (!isChecked){
                                sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                                ArrayList<Integer> listhour = new ArrayList<Integer>();
                                ArrayList<Integer> listminut = new ArrayList<Integer>();
                                ArrayList<Integer> listflag = new ArrayList<Integer>();
                                int temp = sharedPreferences.getInt("lastkeyH",0);
                                for (int i=0;i< temp-1;i++){
                                    listhour.add(sharedPreferences.getInt(String.valueOf(i+1),0));
                                }
                                for (int j = 0; j < temp-1; j++){
                                    listminut.add(sharedPreferences.getInt(String.valueOf(j+1+100), 0));
                                }
                                for (int k = 0;k < temp-1;k++){
                                    listflag.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                                }
                                for (int i = 0;i<listhour.size();i++) {
                                    if (listflag.get(i) == 1) {
                                        Intent intent = new Intent(MainActivity.this, Alarm_brod.class);
                                        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                                        alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        alarmManager[i].cancel(pi);
                                    }
                                }
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                listflag.set(item,0);

                                for (int i = 0;i<listflag.size();i++){
                                    savealaram(listhour.get(i),listminut.get(i),listflag.get(i));
                                }
                                ArrayList<Long> array = new ArrayList<Long>();
                                for(int i = 0,j = 0;i<temp-1;i++,j++){
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(calendar.get(calendar.YEAR),
                                            calendar.get(calendar.MONTH),
                                            calendar.get(calendar.DAY_OF_MONTH),
                                            sharedPreferences.getInt(String.valueOf(i+1), 0),
                                            sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                            0);
                                    long timeInMillis = calendar.getTimeInMillis();
                                    array.add(timeInMillis);
                                }
                                setalaram(array,listflag);

                            }
                        }
                    });
                    recyclerView.setAdapter(ad);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setVisibility(View.VISIBLE);
                }
                int p;
                sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                p = sharedPreferences.getInt("lastkeyH",0);
                ArrayList<Long> array1 = new ArrayList<Long>();
                ArrayList<Integer> fla = new ArrayList<Integer>();
                if (p!=0){
                    for(int i = 0,j = 0;i<p-1;i++,j++){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(calendar.get(calendar.YEAR),
                                calendar.get(calendar.MONTH),
                                calendar.get(calendar.DAY_OF_MONTH),
                                sharedPreferences.getInt(String.valueOf(i+1), 0),
                                sharedPreferences.getInt(String.valueOf(j+1+100), 0),
                                0);
                        long timeInMillis = calendar.getTimeInMillis();
                        array1.add(timeInMillis);
                    }
                    for (int k = 0;k<temp-1;k++){
                        fla.add(sharedPreferences.getInt(String.valueOf(k+1+200), 0));
                    }
                    setalaram(array1,fla);
                }
            }
            else {
                recyclerView.setVisibility(View.GONE);
            }
        }
    }


    private void setalaram(ArrayList<Long> array,ArrayList<Integer> flag) {
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        for(int f=0;f<array.size();f++){
            Calendar rightNow = Calendar.getInstance();
            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
            int currentmin = rightNow.get(Calendar.MINUTE);
            rightNow.set(rightNow.get(rightNow.YEAR),
                    rightNow.get(rightNow.MONTH),
                    rightNow.get(rightNow.DAY_OF_MONTH),
                    currentHour,
                    currentmin,
                    0);
            if (rightNow.getTimeInMillis()<array.get(f) && flag.get(f) == 1) {
                Intent intent = new Intent(this, Alarm_brod.class);
                intent.putExtra("position",f);
                s = getSharedPreferences("setringtone", Context.MODE_PRIVATE);
                intent.putExtra("setringtone",s.getString("ringtoneuri", String.valueOf(Settings.System.DEFAULT_ALARM_ALERT_URI)));
                PendingIntent pi = PendingIntent.getBroadcast(this, f, intent, 0);

                alarmManager[f] = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager[f].set(AlarmManager.RTC_WAKEUP, array.get(f), pi);

                intentArray.add(pi);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is preset.
        getMenuInflater().inflate(com.example.vishal.alarm_clock.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.example.vishal.alarm_clock.R.id.action_settings) {
            return true;
        }
        switch (id){
            case com.example.vishal.alarm_clock.R.id.action_settings:
                Toast.makeText(this,"you clicked setting",Toast.LENGTH_SHORT).show();
                break;
            case com.example.vishal.alarm_clock.R.id.deletealaram:
                sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
                int t = sharedPreferences.getInt("lastkeyH",0);
                if (t != 0){
                    Intent intent = new Intent(this,Delete_alarm.class);
                    startActivityForResult(intent,123);
                }
                else{
                    Toast.makeText(this,"No any alaram for delet",Toast.LENGTH_SHORT).show();
                }
                break;
            case com.example.vishal.alarm_clock.R.id.set_ringtone :
                Intent i = new Intent(this,Set_ringtone.class);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
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
