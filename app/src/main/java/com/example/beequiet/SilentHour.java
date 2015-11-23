package com.example.beequiet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.Timer;
import java.util.TimerTask;
import android.media.AudioManager;
import android.content.Context;
import android.util.Log;

public class SilentHour extends Service {

    Intent intentSend;
    final static String MY_ACTION = "MY_ACTION";

    AudioManager mode;

    int timeLeft;
    Timer timer = new Timer ();
    TimerTask hourlyTask = new TimerTask () {
        @Override
        public void run () {
            timeLeft--;
            // update the button text
            intentSend.putExtra("timeLeft", timeLeft);

            sendBroadcast(intentSend);
        }
    };


    /** indicates how to behave if the service is killed */
    int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        super.onCreate();
        //intentSend = new Intent(BROADCAST_ACTION);
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start silent and the hour here
        intentSend = new Intent();
        intentSend.setAction(MY_ACTION);
        // change the time here should be time you want + 1:
        timeLeft = 61;
        // ------------------------------
        mode = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        timer.schedule (hourlyTask, 0l, 1000*60);

        return mStartMode;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        // Stop the hour here and reactivate button
        timer.cancel();
        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

    }
}
