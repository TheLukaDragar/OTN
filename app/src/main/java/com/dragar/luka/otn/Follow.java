package com.dragar.luka.otn;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Follow extends AppCompatActivity {
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        final Animation animation5 =AnimationUtils.loadAnimation(this,R.anim.blink_anim);
        setContentView(R.layout.activity_follow);
        b1 = findViewById(R.id.button3);
        b1.startAnimation(animation5);


    }



    public void follow(View view) {
        Uri uri = Uri.parse("http://instagram.com/_u/thelukadragar");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/thelukadragar")));
        }


    }


    public void github(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/TheLukaDragar/OTN?files=1")));

    }
}
