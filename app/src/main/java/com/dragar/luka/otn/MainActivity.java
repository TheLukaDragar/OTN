package com.dragar.luka.otn;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.annotation.Target;
import java.util.Locale;


import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    //public static final String SECONDS = "seconds";
    EditText ed2;
    ImageButton  b2;
    Button button;


    private ClipboardManager myClipboard;
    private static final String CHANNEL_ID = "personal_notifications";
    public AdView mAdView;
    String text10;
   // String sekunde10;
    // Obtain the FirebaseAnalytics instance.

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loadData();
        Animation animation =AnimationUtils.loadAnimation(this,R.anim.fadein);
        final Animation animation2 =AnimationUtils.loadAnimation(this,R.anim.blink_anim);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity



            tutorial();

        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();





        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonStartPause.startAnimation(animation);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                    stopService();
                } else {
                    startTimer();
                    startService();

                    MainActivity.this.finish();

                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });








        MobileAds.initialize(this, "ca-app-pub-4526692710511158~4086317652");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {


                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });










        ed2 = findViewById(R.id.edittext);
        ed2.setText(text10);
        ed2.startAnimation(animation2);
        b2 = findViewById(R.id.imageButton2);

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed2.clearAnimation();




            }

            @Override
            public void afterTextChanged(Editable s) {
                saveData();
               // color();

            }
        });


        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClipData abc = myClipboard.getPrimaryClip();
                ClipData.Item item = null;
                if (abc != null) {
                    item = abc.getItemAt(0);
                }

                String text = null;
                if (item != null) {
                    text = item.getText().toString();
                }
                ed2.setText(text);

                Toast.makeText(getApplicationContext(), "Text Pasted",
                        Toast.LENGTH_SHORT).show();
            }
        });









       // new MaterialTapTargetPrompt.Builder(this)
         //       .setTarget(R.id.textView2)
           //     .setPrimaryText("Primary text")

             //   .show();




    }

    private void tutorial() {
        new MaterialTapTargetPrompt.Builder(MainActivity.this)
                .setTarget(R.id.imageButton2)
                .setPrimaryText("Quick Tutorial")
                .setSecondaryText("Tap the paste button to paste you note from your clipboard.")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        { new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                .setTarget(R.id.button)
                                .setPrimaryText("Test")
                                .setSecondaryText("A notification with your text will appear")
                                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                {
                                    @Override
                                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                    {
                                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                                        {
                                            new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                                    .setTarget(R.id.button_start_pause)
                                                    .setPrimaryText("Start")
                                                    .setSecondaryText("Start the countdown that you set OTN will close and notify when the time has passed." +
                                                            "That's it")
                                                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                                    {
                                                        @Override
                                                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                                        {
                                                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                                                            {


                                                                // User has pressed the prompt target
                                                            }
                                                        }
                                                    })
                                                    .show();

                                            // User has pressed the prompt target
                                        }
                                    }
                                })
                                .show();
                            // User has pressed the prompt target
                            // User has pressed the prompt target



                        }
                    }
                })
                .show();



    }

    //private void color() {
      //  Random rnd = new Random();
        //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //final Drawable drawable = new ColorDrawable(color);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //toolbar.(drawable);
      //  }

    //}


    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                savepls();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void savepls() {
        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs2.edit();

        editor.putLong("startTimeInMillis2", mStartTimeInMillis);
        editor.putLong("millisLeft2", mTimeLeftInMillis);
        editor.putBoolean("timerRunning2", mTimerRunning);
        editor.putLong("endTime2", mEndTime);

        editor.apply();



    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText(R.string.pause);
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText(R.string.start);

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }




    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();

        editor.putString(TEXT,ed2.getText().toString());
      //  editor.putString(SECONDS,editText1.getText().toString());
        editor.apply();
   }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text10 = sharedPreferences.getString(TEXT,"");

    }


   // private void dej() {
       // EditText text = (EditText)findViewById(R.id.edittext);
        //String value = text.getText().toString();

      //  createNotificationChannel2();

        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID2)
               // .setSmallIcon(R.mipmap.ic_launcher_round)
               // .setContentTitle(getString(R.string.namen))
               // .setContentText(value)
               // .setPriority(NotificationCompat.PRIORITY_MAX)
               // .setAutoCancel(true)
               // .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
               // .setStyle(new NotificationCompat.BigTextStyle().bigText(value));







        //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);


       // notificationManagerCompat.notify(NOTIFICATION_ID2,builder.build());
       // notificationManagerCompat.cancel(NOTIFICATION_ID);

    //}


    public void obvestilo(View view) {



        EditText text = findViewById(R.id.edittext);
        String value = text.getText().toString();

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)

                .setContentTitle(getString(R.string.namen))
                .setContentText(value)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(value))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))

                .setPriority(NotificationCompat.PRIORITY_MAX);







        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int NOTIFICATION_ID = 1;
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }
    private void  createNotificationChannel()
    {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);


        }



    }

   // private void  createNotificationChannel2()
   // {

       // if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
       // {

           // CharSequence name = "Personal Notifications";
           // String description = "Include all the personal notifications";
           // int importance = NotificationManager.IMPORTANCE_DEFAULT;

            //NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID2,name,importance);

            //notificationChannel.setDescription(description);

           // NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
           // assert notificationManager != null;
           // notificationManager.createNotificationChannel(notificationChannel);


      //  }



  //  }

    public void clear(View view) {
        ed2.setText("");


    }
    //public void onBackPressed () {
      //  MainActivity.this.finish();
     // moveTaskToBack (false);
     //  saveData();

   // }



    public void startService() {


       // String input= textView.getText().toString();
        Intent serviceIntent = new Intent(this, ExampleService.class);
       // serviceIntent.putExtra("InputExtra", input);

        ContextCompat.startForegroundService(this,serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.MENU:
                Intent myIntent = new Intent(MainActivity.this, Follow.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            break;
            case R.id.MENU2:
                MainActivity.this.finish();
                stopService();

                break;
        }
        return true;
    }
}
