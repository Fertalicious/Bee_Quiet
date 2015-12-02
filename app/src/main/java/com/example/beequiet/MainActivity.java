package com.example.beequiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.widget.ToggleButton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.app.AlarmManager;
import android.app.PendingIntent;
import java.util.Calendar;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import java.io.File;
import java.lang.NumberFormatException;
import android.media.AudioManager;


public class MainActivity extends AppCompatActivity {

    private Button btnHour;
    int timeLeft;
    MyReceiver myReceiver;
    ToggleButton onOff;
    private AudioManager myAudioManager;

    //Russ commit test 2

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            timeLeft = arg1.getIntExtra("timeLeft", 0);
            updateUI(timeLeft);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        toolbar.setBackground(getDrawable(R.drawable.beequietbanner));
        btnHour = (Button) findViewById(R.id.buttonHour);
        onOff = (ToggleButton) findViewById(R.id.toggleButton);
    }

    @Override
    protected void onStart() {
        //Register BroadcastReceiver
        //to receive event from the service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SilentHour.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void updateUI(int time) {
        if(time == 0){
            stopService();
            // enable button
            btnHour.setText("QUIET FOR AN HOUR");
            btnHour.setEnabled(true);
        }
        else {
            btnHour.setText(Integer.toString(time) + " mins");
        }
    }

    public void clickView(View v){
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    public void clickHour(View v){
        // Silent for an hour, set the text of the button to current amount of time left
        startService();
        // disable button
        btnHour.setEnabled(false);
    }

    public void clickDeactivate(View v){
        // Stop the effects of the app or vice versa
        if(!onOff.isChecked()){
            stopService();
            btnHour.setText("QUIET FOR AN HOUR");
            btnHour.setEnabled(false);
        }
        else
        {
            btnHour.setEnabled(true);
            startService(new Intent(getBaseContext(), WeekSilentService.class));
        }

    }

    // Method to start the service
    public void startService() {
        startService(new Intent(getBaseContext(), SilentHour.class));
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), SilentHour.class));
        stopService(new Intent(getBaseContext(), WeekSilentService.class));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver);
        super.onStop();
    }


    /*
    public class AlarmReciever extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            // here you can start an activity or service depending on your need

        }

    }
*/
/*
    public class checkSilent extends IntentService {
        private static final String TAG = "MyService";

        // Service no-arg constructor
        public checkSilent() {
            super(TAG);
        }

        @Override
        public void onHandleIntent(Intent intent) {
        // runs checkSchedule function to determine if current time is within silent time ranges
            if(checkSchedule())
                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            else
                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }
*/
    public boolean checkSchedule() {
        boolean silentTime = false;
        String daySchedule;
        String weekDay = "";
        String timeStart = "";
        String timeEnd = "";
        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        String[] timeStartArray;
        String[] timeEndArray;
        int startHour = 0;
        int startMinute = 0;
        int endHour = 0;
        int endMinute= 0;

        FileInputStream fIn = null;
        try {
            fIn = openFileInput("time.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));


        try{



            while ((daySchedule = reader.readLine()) != null) {
                // process the current line, splitting into array of Strings
                String[] str_array = daySchedule.split("-");

                for(int i = 0; i < str_array.length; i++)
                {
                    if(i == 0) {
                        //checks if first String in array is equal to current system day, breaks out if not
                        weekDay = str_array[i];
                        if(!weekDay.equals(currentDay))
                            break;
                    }
                    else if(i%2 == 1) {
                        timeStart = str_array[i];
                        timeStartArray = timeStart.split(":");
                        try {
                            startHour = Integer.parseInt(timeStartArray[0]);
                        } catch(NumberFormatException nfe) {}
                        try {
                            startMinute = Integer.parseInt(timeStartArray[1]);
                        } catch(NumberFormatException nfe) {}

                    }
                    else {
                        timeEnd = str_array[i];
                        timeEndArray = timeEnd.split(":");
                        try {
                            endHour = Integer.parseInt(timeEndArray[0]);
                        } catch(NumberFormatException nfe) {}
                        try {
                            endMinute = Integer.parseInt(timeEndArray[1]);
                        } catch(NumberFormatException nfe) {}

                        //at this point there is a timeStart and timeEnd, then compares system time to the range
                        if(currentHour >= startHour && currentHour < endHour)
                            silentTime = true;
                    }

                }

                if(weekDay.equals(currentDay))
                    break;
            }

        }
        catch (Exception e){
        }


        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //returns false if the current system time doesn't fall into any start or end ranges for current day
        return silentTime;
    }

    public void checkFileExist(){
        try{ FileInputStream fis = null;
            fis = openFileInput("time.txt");
            // File file = new File("time.txt");
            // boolean exists = file.isFile();
        }
        catch (Exception e){

        }
    }

/*
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
    }*/

    // Test github
}
