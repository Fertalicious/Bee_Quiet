package com.example.beequiet;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class WeeklyActivity extends AppCompatActivity {

    //Uses a listview to contain the hours
    ListView lv;
    ArrayList<String> option;
    public static ArrayAdapter<String> adapter;

    //flag for checking if start has been clicked
    boolean checkStart;
    String startTime, endTime;
    String dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        toolbar.setBackground(getDrawable(R.drawable.beequietbanner));

        startTime = "";
        endTime = "";
        checkStart = false;
        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());//Need to access actual day of week, for now default to monday

        checkFileExist();

        //writeDaytoFile();// if you want to reset your file use this
        //Sets up the list view and arraylist to be used
        lv = (ListView)findViewById(R.id.listView);
        option = new ArrayList<String>();
        addOption();
        listClick();
    }
    private void addOption() {
        //not sure what to do for 15 , 30 , 45
        option.add(0, "12");
        option.add(1, "1");
        option.add(2, "2");
        option.add(3, "3");
        option.add(4, "4");
        option.add(5, "5");
        option.add(6, "6");
        option.add(7, "7");
        option.add(8, "8");
        option.add(9, "9");
        option.add(10, "10");
        option.add(11, "11");
        option.add(12, "12");
        option.add(13, "1");
        option.add(14, "2");
        option.add(15, "3");
        option.add(16, "4");
        option.add(17, "5");
        option.add(18, "6");
        option.add(19, "7");
        option.add(20, "8");
        option.add(21, "9");
        option.add(22, "10");
        option.add(23, "11");

        adapter = new ArrayAdapter<String>(this, R.layout.list_view, option);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //for setting pressing time
    private void listClick() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            int mPosition = -1;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.d("Button Test", "12am is being pressed");
                        addBlock(dayOfWeek, 0);
                        if(mPosition!=-1) {
                            //reset color of previously pressed item to green
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        //set current item to gray
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        //put value of currently selected item to mPosition.
                        mPosition = position;
                        break;
                    case 1:
                        addBlock(dayOfWeek, 1);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 2:
                        addBlock(dayOfWeek, 2);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 3:
                        addBlock(dayOfWeek, 3);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 4:
                        addBlock(dayOfWeek, 4);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 5:
                        addBlock(dayOfWeek, 5);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 6:
                        addBlock(dayOfWeek, 6);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 7:
                        addBlock(dayOfWeek, 7);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 8:
                        addBlock(dayOfWeek, 8);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 9:
                        addBlock(dayOfWeek, 9);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 10:
                        addBlock(dayOfWeek, 10);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 11:
                        addBlock(dayOfWeek, 11);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 12:
                        addBlock(dayOfWeek, 12);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 13:
                        addBlock(dayOfWeek, 13);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 14:
                        addBlock(dayOfWeek, 14);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 15:
                        addBlock(dayOfWeek, 15);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 16:
                        addBlock(dayOfWeek, 16);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 17:
                        addBlock(dayOfWeek, 17);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 18:
                        addBlock(dayOfWeek, 18);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 19:
                        addBlock(dayOfWeek, 19);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 20:
                        addBlock(dayOfWeek, 20);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 21:
                        addBlock(dayOfWeek, 21);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 22:
                        addBlock(dayOfWeek, 22);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                    case 23:
                        addBlock(dayOfWeek, 23);
                        if(mPosition!=-1) {
                            lv.getChildAt(mPosition).setBackgroundColor(Color.GREEN);
                        }
                        lv.getChildAt(position).setBackgroundColor(Color.GRAY);
                        mPosition = position;
                        break;
                }

                return false;
            }
        });
    }

    //get day of the week from xml
    public void setDayOfWeek(View v)
    {
        dayOfWeek = v.getTag().toString();// will return the string dayofWeek
    }

    //Will activate when clicked for the first time, or after end block
    public void addBlock(String dayOfWeek, int hour){
        chooseMin(hour);
    }


    //gets the minute from the hour selected
    public void chooseMin(final int hour){

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.android_seekbar_dialog, (ViewGroup) findViewById(R.id.your_dialog_root_element));
        final AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setView(layout);
        alert.setTitle("Set Time");

        // Set a seekbar(slider) view to get user input
        alert.setMessage(dayOfWeek + " " + String.valueOf(hour) + " : 00");

        alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //swap checkStart
                if (!checkStart)
                    checkStart = true;
                else
                {
                    checkStart = false;
                    writeFile(readFile());
                }
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "Time Set: " + startTime + " " + endTime, duration);
                toast.show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Context context = getApplicationContext();
                CharSequence text = "Cancelled Block to be added";
                int duration = Toast.LENGTH_SHORT;
                if (!checkStart)
                    startTime = "";
                else
                    endTime = "";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        final SeekBar input = (SeekBar)layout.findViewById(R.id.seekBar1);


        input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 00;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                alertDialog.setMessage(dayOfWeek + " " + hour + " : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                alertDialog.setMessage(dayOfWeek + " " + hour + " : " + progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                alertDialog.setMessage(dayOfWeek + " " + hour + " : " + progress);

                if (!checkStart)
                    startTime = hour + ":" + progress;
                else
                    endTime = hour + ":" + progress;
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar: " + startTime + " " + endTime, Toast.LENGTH_SHORT).show();
            }
        });

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

    //writes the updated time into the table
    public void writeFile(String ret){

        String[] line = ret.split("\n");

        Log.d("write file","am i writing " + ret);
        //loop through to find correct date
        for (int i = 0; i < line.length; i++) {
            String token[] = line[i].split("-");

            if(token[0].equals(dayOfWeek)) {
                line[i] += "-" + startTime + "-" + endTime;
                Log.d("Correct week updated?",line[i]);
            }
            //this will look like Monday "2:00-4:45" "5:00-6:00"
            //in the text file
        }
        Log.d("write file", "am i writing " + line[0]);
        try {

            FileOutputStream writer = openFileOutput("time.txt", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
            BufferedWriter BR = new BufferedWriter(new OutputStreamWriter(writer));
            //loop through to find correct date
            for (int i = 0; i < line.length; i++) {
                BR.write(line[i]);
                BR.newLine();
            }
            BR.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void clickImport(View v){
        Intent intent = new Intent(this, ImportActivity.class);
        startActivity(intent);
    }

    public void clickMonth(View v){
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }
}
