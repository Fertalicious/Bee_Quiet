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


public class MainActivity extends AppCompatActivity {

    private Button btnHour;
    int timeLeft;
    MyReceiver myReceiver;
    ToggleButton onOff;
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
        //to receive event from our service
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
            btnHour.setText(Integer.toString(time));
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
        }

    }

    // Method to start the service
    public void startService() {
        startService(new Intent(getBaseContext(), SilentHour.class));
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), SilentHour.class));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver);
        super.onStop();
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
