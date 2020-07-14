package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView tvSplash;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;   //to lock the orientation
        setRequestedOrientation(o);

        tvSplash = findViewById(R.id.tvsplash);

        Typeface tf2 = Typeface.createFromAsset(getAssets(),
                "fonts/Chantelli_Antiqua.ttf");
        tvSplash.setTypeface(tf2);
        animation = AnimationUtils.loadAnimation(this , R.anim.a1);     //start athe animartion
        tvSplash.startAnimation(animation);      //load the animation

        new Thread(new Runnable() {
            @Override
            public void run() {             //we need sleep for splash and we have sleep in thread only hence we create a thread
                try
                {
                    Thread.sleep(4000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent a = new Intent(SplashActivity.this , MainActivity.class);
                startActivity(a);
                finish();           //it'll never be seen again also even back pressed(only at the start of the app)
            }
        }).start();


    }
}
