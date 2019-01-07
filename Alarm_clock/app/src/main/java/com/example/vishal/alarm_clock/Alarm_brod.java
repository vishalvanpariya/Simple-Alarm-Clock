package com.example.vishal.alarm_clock;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

public class Alarm_brod extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = Uri.parse(intent.getExtras().getString("setringtone"));
        int a=intent.getExtras().getInt("position");
        Log.w("hahha", String.valueOf(a));
        MediaPlayer mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.start();
        Intent i = new Intent(context,alarm_run.class);
        i.putExtra("position",a);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
