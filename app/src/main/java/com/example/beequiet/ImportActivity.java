package com.example.beequiet;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ImportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        toolbar.setBackground(getDrawable(R.drawable.beequietbanner));


    }


    //clear button
    public void clickExternal(View v)
    {
        clear();
    }

    public void clear(){
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
}
