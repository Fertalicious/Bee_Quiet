package com.example.beequiet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ViewActivity extends AppCompatActivity {

    //Uses a listview to contain the hours
    ListView lv;
    ArrayList<String> option;
    public static ArrayAdapter<String> adapter;

    //flag for checking if start has been clicked
    String dayOfWeek;
    Button mondayButton;
    Button tuesdayButton;
    Button wednesdayButton;
    Button thursdayButton;
    Button fridayButton;
    Button saturdayButton;
    Button sundayButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        toolbar.setBackground(getDrawable(R.drawable.beequietbanner));

        mondayButton = (Button) findViewById(R.id.mondayButton);
        tuesdayButton = (Button) findViewById(R.id.tuesdayButton);
        wednesdayButton = (Button) findViewById(R.id.wednesdayButton);
        thursdayButton = (Button) findViewById(R.id.thursdayButton);
        fridayButton = (Button) findViewById(R.id.fridayButton);
        saturdayButton = (Button) findViewById(R.id.saturdayButton);
        sundayButton = (Button) findViewById(R.id.sundayButton);

        mondayButton.setBackgroundResource(android.R.drawable.btn_default);
        tuesdayButton.setBackgroundResource(android.R.drawable.btn_default);
        wednesdayButton.setBackgroundResource(android.R.drawable.btn_default);
        thursdayButton.setBackgroundResource(android.R.drawable.btn_default);
        fridayButton.setBackgroundResource(android.R.drawable.btn_default);
        saturdayButton.setBackgroundResource(android.R.drawable.btn_default);
        sundayButton.setBackgroundResource(android.R.drawable.btn_default);

        Button viewButton = (Button) findViewById(R.id.buttonMonthly);
        viewButton.setBackgroundColor(0xff009688);

        checkFileExist();

        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());//Need to access actual day of week, for now default to monday

        lv = (ListView)findViewById(R.id.listView);
        option = new ArrayList<String>();
        checkSchedule(dayOfWeek);
    }

    public boolean checkSchedule(String dayOfWeek) {

        try {
            FileInputStream inputStream = openFileInput("time.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {

                    String[] line = receiveString.split("-");

                    if (dayOfWeek.equals(line[0])) {

                        if(lv.getAdapter() != null) {
                            lv.setAdapter(null);//clear it first
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        }

                        for (int i = 0; i < line.length - 2; i++) {
                            int count = 0;
                            Log.d("length ", line.length + " ");

                            Log.d("here", line[i + 1] + "-" + line[i + 2]);
                            if((i+1) % 2 == 1 ) {
                                option.add(line[i + 1] + "-" + line[i + 2]);
                                count++;
                            }
                        }
                        //lv.setStackFromBottom(true);
                        adapter = new ArrayAdapter<String>(this, R.layout.list_view, option);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }


                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        //returns false if the current system time doesn't fall into any start or end ranges for current day
        return false;
    }

    //initialization of file
    public void checkFileExist(){
        try{
            if(readFile().equals(""))
                writeDaytoFile();}
        catch (Exception e){

        }
    }
    //write the days of the week to a file called time.txt, assuming
    //user doesn't have it (opened this activity to create)
    public void writeDaytoFile(){
        try {
            String nodeValue = "Monday Tuesday Wednesday Thursday Friday Saturday Sunday";
            FileOutputStream writer = openFileOutput("time.txt", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
            BufferedWriter BR = new BufferedWriter(new OutputStreamWriter(writer));
            String[] week = nodeValue.split(" ");
            for (String day: week) {
                BR.write(day);
                BR.newLine();
                BR.newLine();
            }
            BR.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //read from file, and then write
    private String readFile() {

        String ret = "";

        try {
            FileInputStream inputStream = openFileInput("time.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString + "\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("TABLE: \n", ret);
        return ret;
    }

    public void setDayOfWeek(View v)
    {
        dayOfWeek = v.getTag().toString();// will return the string dayofWeek
        checkSchedule(dayOfWeek);
        mondayButton.setBackgroundResource(android.R.drawable.btn_default);
        tuesdayButton.setBackgroundResource(android.R.drawable.btn_default);
        wednesdayButton.setBackgroundResource(android.R.drawable.btn_default);
        thursdayButton.setBackgroundResource(android.R.drawable.btn_default);
        fridayButton.setBackgroundResource(android.R.drawable.btn_default);
        saturdayButton.setBackgroundResource(android.R.drawable.btn_default);
        sundayButton.setBackgroundResource(android.R.drawable.btn_default);
        v.setBackgroundColor(0xff009688);
    }

    public void clickImport(View v){
        Intent intent = new Intent(this, ImportActivity.class);
        startActivity(intent);
    }

    public void clickWeekly(View v){
        Intent intent = new Intent(this, WeeklyActivity.class);
        startActivity(intent);
    }

}
