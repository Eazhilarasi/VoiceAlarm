package com.example.eazhilarasi.voicealarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {
    public static MediaPlayer mediaPlayer;
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        PendingIntent Sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        NotificationManager manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);


        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context.getApplicationContext().getFilesDir()+"/audio.m4a");
            mediaPlayer.prepare();
        } catch (IOException e) {

        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //intent to call the activity which shows on ringing


        //display that alarm is ringing
        Toast.makeText(context, "Alarm Ringing...!!!", Toast.LENGTH_LONG).show();
        Intent i = new Intent();

        i.setClassName("com.example.eazhilarasi.voicealarm", "com.example.eazhilarasi.voicealarm.PutOffAlarm");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
