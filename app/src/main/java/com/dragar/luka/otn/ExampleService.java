package com.dragar.luka.otn;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import static com.dragar.luka.otn.APP.CHANNEL_ID8;
import static com.dragar.luka.otn.MainActivity.SHARED_PREFS;
import static com.dragar.luka.otn.MainActivity.TEXT;


public class ExampleService extends Service {

    private static final int NOTIFICATION_ID8 = 8 ;


    private String text;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning2;

    private long mTimeLeftInMillis2;
    private long mEndTime2;

    public ExampleService() {
    }


    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "OTN will notify ",
                Toast.LENGTH_SHORT).show();
        loadData();

        super.onCreate();
    }
    //gig

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT,"");
        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);

        long mStartTimeInMillis2 = prefs2.getLong("startTimeInMillis2", 600000);
        mTimeLeftInMillis2 = prefs2.getLong("millisLeft2", mStartTimeInMillis2);
        mTimerRunning2 = prefs2.getBoolean("timerRunning2", false);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();

       // if (!sekunde.equalsIgnoreCase("")) {









        String input = intent.getStringExtra("InputExtra");

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, APP.CHANNEL_ID3)
                .setContentTitle("ticking...")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .build();


        startForeground(1, notification);

        return START_NOT_STICKY;

    }

    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis2 = millisUntilFinished;

            }

            @Override
            public void onFinish() {
                finito2();

                mTimerRunning2 = false;


            }
        }.start();

        mTimerRunning2 = true;
    }
    private void finito2() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID8)
                .setSmallIcon(R.mipmap.ic_launcher_round)

                .setContentTitle(getString(R.string.namen))
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))

                .setPriority(NotificationCompat.PRIORITY_MAX);







        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID8,builder.build());
        stopSelf();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();


        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
