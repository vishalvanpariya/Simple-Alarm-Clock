package com.example.vishal.alarm_clock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class Delete_alarm extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    Deletadapter ad;
    LinkedList<Integer> a = new LinkedList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vishal.alarm_clock.R.layout.activity_delete_alarm);
        Toolbar toolbar = (Toolbar) findViewById(com.example.vishal.alarm_clock.R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(com.example.vishal.alarm_clock.R.id.delet_recycler);
        sharedPreferences = getSharedPreferences("alaramtime", Context.MODE_PRIVATE);
        int t;
        ArrayList<Integer> arrayListhour = new ArrayList<Integer>();
        ArrayList<Integer> arrayListmin = new ArrayList<Integer>();
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
            ad = new Deletadapter(this,arrayListhour,arrayListmin);
            ad.setCallback(new Deletadapter.Callback() {
                @Override
                public void onCheckedChanged(String item, boolean isChecked) {
                    if(isChecked) {
                        a.add(Integer.parseInt(item)+1);
                    }
                    if (!isChecked){
                        int o=0;
                        for (int i = 0;i<a.size();i++){
                            if (a.get(i)-1 == Integer.parseInt(item)){
                                o = i;
                            }
                        }
                        a.remove(o);
                    }
                }
            });
            recyclerView.setAdapter(ad);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else {

        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is preset.
        getMenuInflater().inflate(com.example.vishal.alarm_clock.R.menu.menu_delete, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == com.example.vishal.alarm_clock.R.id.delet){
            String s = "";
            for (int i = 0;i<a.size();i++){
                s = s + a.get(i);
            }
            Intent intent = new Intent();
            intent.putExtra("delete",s);
            setResult(456, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
