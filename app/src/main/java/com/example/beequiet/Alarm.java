package com.example.beequiet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Stack;


public class Alarm extends BroadcastReceiver {
    public Alarm() {
    }

    int hour;
    int minute;
    String dayOfWeek;
    Stack schedule = new Stack();
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show();
        Log.d("WeeklyActivityService", "ALARM!!");
        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        try{
            checkSchedule(dayOfWeek);
        }
        catch(Exception e){

        }
        //---------------------------------------------------------------------------------------------
        // Based on if this is the first time or second time in a block, set silent or set normal here
        // Maybe a flag to determine if we set silent or normal
        //---------------------------------------------------------------------------------------------
        /* Example:
        if(firstTime)
        {
            mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }else{
            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        */

    }

    public void SetAlarm(Context context)
    {
        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar
        Calendar cal = new GregorianCalendar();
        String time = schedule.pop().toString();

        //---------------------------------------------------------------------------------------------------------------------------
        // Set these based on file contents, I(Kyle) was unable to reliably get these from the file so i just scrapped the mess I had
        //---------------------------------------------------------------------------------------------------------------------------
        cal.add(Calendar.DAY_OF_WEEK, cur_cal.get(Calendar.DAY_OF_WEEK)); // Set day of week, monday,tuesday, etc, based on file
        cal.set(Calendar.HOUR_OF_DAY, hour);  // change hour based on file
        cal.set(Calendar.MINUTE, minute);  // change minute based on file

        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));

        // Not sure if these have to be set or not
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        //-------------------------------------------

        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);
        Log.d("WeeklyActivityService", "ALARM SET");
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public boolean checkSchedule(String dayOfWeek) throws IOException{

        try {
            FileInputStream fin = new FileInputStream("time.txt");

            if ( fin != null ) {

                InputStreamReader inputStreamReader = new InputStreamReader(fin);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {

                    String[] line = receiveString.split("-");

                    if (dayOfWeek.equals(line[0])) {

                        for (int i = 0; i < line.length - 1; i++) {
                            int count = 0;
                            Log.d("length ", line.length + " ");
                            Log.d("here", line[i + 1]);
                            schedule.push(line[i+1]);
                            }
                        }
                    }


                }

                fin.close();
            }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        //returns false if the current system time doesn't fall into any start or end ranges for current day
        return false;
    }
}
