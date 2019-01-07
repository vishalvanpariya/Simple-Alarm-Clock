package com.example.vishal.alarm_clock;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class Set_ringtone extends AppCompatActivity {

    private Uri mCurrentSelectedUri;

    private View.OnClickListener mCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof CheckedTextView) {
                CheckedTextView checkedTextView = (CheckedTextView) v;
                checkedTextView.setChecked(!checkedTextView.isChecked());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vishal.alarm_clock.R.layout.activity_set_ringtone);

        final CheckedTextView musicCb = findViewById(com.example.vishal.alarm_clock.R.id.cb_music);
        musicCb.setOnClickListener(mCheckBoxClickListener);

        final CheckedTextView notificationCb = findViewById(com.example.vishal.alarm_clock.R.id.cb_notification);
        notificationCb.setOnClickListener(mCheckBoxClickListener);

        final CheckedTextView ringtoneCb = findViewById(com.example.vishal.alarm_clock.R.id.cb_ringtone);
        ringtoneCb.setOnClickListener(mCheckBoxClickListener);

        final CheckedTextView alarmCb = findViewById(com.example.vishal.alarm_clock.R.id.cb_alarm);
        alarmCb.setOnClickListener(mCheckBoxClickListener);

        final SwitchCompat playRingtoneSwitch = findViewById(com.example.vishal.alarm_clock.R.id.switch_play_ringtone);
        final SwitchCompat defaultSwitch = findViewById(com.example.vishal.alarm_clock.R.id.switch_default_ringtone);
        final SwitchCompat silentSwitch = findViewById(com.example.vishal.alarm_clock.R.id.switch_silent_ringtone);

        final TextView ringtoneTv = findViewById(com.example.vishal.alarm_clock.R.id.tv_ringtone_info);

        findViewById(com.example.vishal.alarm_clock.R.id.btn_pick_ringtone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validate if at least one ringtone type is selected.
                if (!musicCb.isChecked()
                        && !notificationCb.isChecked()
                        && alarmCb.isChecked()
                        && musicCb.isChecked()) {

                    Toast.makeText(Set_ringtone.this, com.example.vishal.alarm_clock.R.string.error_no_ringtone_type,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Application needs read storage permission for Builder.TYPE_MUSIC .
                if (ActivityCompat.checkSelfPermission(Set_ringtone.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    RingtonePickerDialog.Builder ringtonePickerBuilder = new RingtonePickerDialog
                            .Builder(Set_ringtone.this, getSupportFragmentManager())

                            //Set title of the dialog.
                            //If set null, no title will be displayed.
                            .setTitle("Select ringtone")

                            //set the currently selected uri, to mark that ringtone as checked by default.
                            //If no ringtone is currently selected, pass null.
                            .setCurrentRingtoneUri(mCurrentSelectedUri)

                            //Allow user to select default ringtone set in phone settings.
                            .displayDefaultRingtone(defaultSwitch.isChecked())

                            //Allow user to select silent (i.e. No ringtone.).
                            .displaySilentRingtone(silentSwitch.isChecked())

                            //set the text to display of the positive (ok) button.
                            //If not set OK will be the default text.
                            .setPositiveButtonText("SET RINGTONE")

                            //set text to display as negative button.
                            //If set null, negative button will not be displayed.
                            .setCancelButtonText("CANCEL")

                            //Set flag true if you want to play the sample of the clicked tone.
                            .setPlaySampleWhileSelection(playRingtoneSwitch.isChecked())

                            //Set the callback listener.
                            .setListener(new RingtonePickerListener() {
                                @Override
                                public void OnRingtoneSelected(@NonNull String ringtoneName, Uri ringtoneUri) {
                                    mCurrentSelectedUri = ringtoneUri;
                                    SharedPreferences s = getSharedPreferences("setringtone", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = s.edit();
                                    editor.putString("ringtoneuri", String.valueOf(ringtoneUri));
                                    editor.apply();
                                    ringtoneTv.setText(String.format("Name : %s\nUri : %s", ringtoneName, ringtoneUri));
                                }
                            });


                    //Add the desirable ringtone types.
                    if (musicCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_MUSIC);
                    if (notificationCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_NOTIFICATION);
                    if (ringtoneCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_RINGTONE);
                    if (alarmCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_ALARM);

                    //Display the dialog.
                    ringtonePickerBuilder.show();
                } else {
                    ActivityCompat.requestPermissions(Set_ringtone.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123);
                }
            }
        });
    }
}
