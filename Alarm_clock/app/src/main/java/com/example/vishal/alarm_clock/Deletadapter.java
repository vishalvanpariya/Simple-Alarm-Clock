package com.example.vishal.alarm_clock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.String.*;

public class Deletadapter extends RecyclerView.Adapter<Deletadapter.DeletHolder> {

    Context ctx;
    ArrayList<Integer> s1,s2;
    CheckBox checkBox;
    private Callback callback;

    public Deletadapter(Context ctx, ArrayList<Integer> s1, ArrayList<Integer> s2) {
        this.ctx=ctx;
        this.s1=s1;
        this.s2=s2;
    }


    @NonNull
    @Override
    public Deletadapter.DeletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myinflater = LayoutInflater.from(ctx);
        View myview = myinflater.inflate(com.example.vishal.alarm_clock.R.layout.rowfordelete,parent,false);
        return new DeletHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull DeletHolder holder, final int position) {
        if (s1.get(position) > 12){
            holder.t1.setText(String.valueOf(s1.get(position)-12));
            holder.t3.setText("PM");
        }
        else {
            holder.t1.setText(String.valueOf(s1.get(position)));
            holder.t3.setText("AM");
        }
        holder.t2.setText(valueOf(s2.get(position)));
        holder.c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Invoke the callback
                if(callback != null){
                    callback.onCheckedChanged(String.valueOf(position),isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return s1.size();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onCheckedChanged(String item, boolean isChecked);
    }

    public class DeletHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        CheckBox c;
        public DeletHolder(View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(com.example.vishal.alarm_clock.R.id.t1);
            t2 = (TextView)itemView.findViewById(com.example.vishal.alarm_clock.R.id.t2);
            t3 = (TextView)itemView.findViewById(com.example.vishal.alarm_clock.R.id.pmoram);
            c = (CheckBox)itemView.findViewById(com.example.vishal.alarm_clock.R.id.checkBox);
        }
    }
}
