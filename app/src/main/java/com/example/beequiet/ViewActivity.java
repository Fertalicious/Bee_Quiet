package com.example.beequiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    //Uses a listview to contain the hours
    ListView lv;
    ArrayList<String> option;
    public static ArrayAdapter<String> adapter;

    //flag for checking if start has been clicked
    String dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        toolbar.setBackground(getDrawable(R.drawable.beequietbanner));
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
