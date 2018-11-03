package com.dragar.luka.otn;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
        import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
        import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import static com.dragar.luka.otn.APP.CHANNEL_ID4;
import static com.dragar.luka.otn.APP.CHANNEL_ID8;
import static com.dragar.luka.otn.MainActivity.SECONDS;
import static com.dragar.luka.otn.MainActivity.TEXT;
import static com.dragar.luka.otn.MainActivity.SHARED_PREFS;


public class ExampleService extends Service {

    private static final int NOTIFICATION_ID4 = 5 ;
    private static final int NOTIFICATION_ID8 = 8 ;


    private String text;
    private String sekunde;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning2;

    private long mStartTimeInMillis2;
    private long mTimeLeftInMillis2;
    private long mEndTime2;






    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "service on creare",
                Toast.LENGTH_SHORT).show();
        loadData();

        super.onCreate();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT,"");
        sekunde =sharedPreferences.getString(SECONDS,"");
        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);

        mStartTimeInMillis2 = prefs2.getLong("startTimeInMillis2", 600000);
        mTimeLeftInMillis2 = prefs2.getLong("millisLeft2", mStartTimeInMillis2);
        mTimerRunning2 = prefs2.getBoolean("timerRunning2", false);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();

        if (!sekunde.equalsIgnoreCase("")) {


            final Integer seconds = Integer.valueOf(sekunde);
            CountDownTimer countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millis) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(), "kr dela ",
                            Toast.LENGTH_SHORT).show();
                    finito();















                }

            }.start();

        }






        String input = intent.getStringExtra("InputExtra");

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, APP.CHANNEL_ID3)
                .setContentTitle("STANDBY")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();


        startForeground(1, notification);

        return START_NOT_STICKY;

    }

    private void finito() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID4)
                .setSmallIcon(R.mipmap.ic_launcher_round)

                .setContentTitle(getString(R.string.namen))
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))

                .setPriority(NotificationCompat.PRIORITY_MAX);







        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID4,builder.build());
    }

    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis2 = millisUntilFinished;

            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "!!!!!!!!!!!!! ",
                        Toast.LENGTH_SHORT).show();
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
