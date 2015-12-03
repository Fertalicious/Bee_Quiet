package com.example.beequiet;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

public class WeekSilentService extends Service {

    Intent intentSend;
    final static String MY_ACTION = "MY_ACTION";

    AudioManager mode;

    Alarm alarm = new Alarm();

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
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start the alarm
        mode = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);

        alarm.SetAlarm(this);
        //alarm.SetAlarm2(this);
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
        // Stop the service, set phone to normal
        alarm.CancelAlarm(this);
        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

    }
}
