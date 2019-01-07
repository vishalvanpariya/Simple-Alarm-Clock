package com.example.vishal.alarm_clock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by vishal on 28-02-2018.
 */

public class MyOwnAdapter extends RecyclerView.Adapter<MyOwnAdapter.MyOwnHolder> {
    Context ctx;
    ArrayList<Integer> s1,s2,s3;
    Switch aSwitch;
    private Call callback;

    public MyOwnAdapter(Context ctx, ArrayList<Integer> s1, ArrayList<Integer> s2,ArrayList<Integer> s3){
        this.ctx=ctx;
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
    }

    @Override
    public MyOwnAdapter.MyOwnHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myinflater = LayoutInflater.from(ctx);
        View myview = myinflater.inflate(com.example.vishal.alarm_clock.R.layout.row,parent,false);
        return new MyOwnHolder(myview);
    }

    @Override
    public void onBindViewHolder(MyOwnAdapter.MyOwnHolder holder, final int position) {
        if (s1.get(position) > 12){
            holder.t1.setText(String.valueOf(s1.get(position)-12));
            holder.t3.setText("PM");
        }
        else {
            holder.t1.setText(String.valueOf(s1.get(position)));
            holder.t3.setText("AM");
        }

        holder.t2.setText(String.valueOf(s2.get(position)));
        if (s3.get(position) == 1){
            holder.h.setChecked(true);
        }
        holder.h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Invoke the callback
                if(callback != null){
                    callback.onCheckedChanged(position,isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (s1.size());
    }
    public void setCallback(Call callback) {
        this.callback = callback;
    }

    public interface Call {
        void onCheckedChanged(int item, boolean isChecked);
    }



    public class MyOwnHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        Switch h;
        public MyOwnHolder(View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(com.example.vishal.alarm_clock.R.id.t1);
            t2 = (TextView)itemView.findViewById(com.example.vishal.alarm_clock.R.id.text2);
            t3 = (TextView)itemView.findViewById(com.example.vishal.alarm_clock.R.id.pm_or_am);
            h = (Switch)itemView.findViewById(com.example.vishal.alarm_clock.R.id.switch1);
        }
    }
}
