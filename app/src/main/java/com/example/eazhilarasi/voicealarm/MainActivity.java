package com.example.eazhilarasi.voicealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    MediaRecorder recorder;
    MediaPlayer mediaPlayer;
    TimePicker timepicker;
    long milliseconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timepicker = (TimePicker)findViewById(R.id.timePicker);
        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                milliseconds = hourOfDay * 3600000;
                milliseconds = minute * 60000;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                int offset = cal.getTimeZone().getOffset(cal.getTimeInMillis());
                milliseconds = cal.getTimeInMillis() + offset;

                Toast.makeText(MainActivity.this, String.valueOf(milliseconds) + " : " + String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void record(View view)
    {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilesDir() + "/audio.m4a");
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();

    }

    public void stop(View view)
    {
        recorder.stop();
        recorder.reset();
        recorder.release();
    }

    public void play(View view)
    {
        Intent AlarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);


        AlarmManager AlmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent Sender = PendingIntent.getBroadcast(this, 0, AlarmIntent, 0);
        AlmMgr.set(AlarmManager.RTC_WAKEUP, milliseconds, Sender);

    }
}
