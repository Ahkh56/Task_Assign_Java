package com.pixelpk.task_assign_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixelpk.task_assign_java.Main_Section.MainActivity;

public class Splash_Screen extends AppCompatActivity
{
    private static final int SPLASH_TIME_OUT = 3000;
    Animation fade,slide;
    ImageView logo;
    TextView  text_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        intialize_view();

        //Setting Animation
        logo.setAnimation(fade);
        text_logo.setAnimation(slide);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //Calling new Activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    private void intialize_view()
    {
        logo      = findViewById(R.id.splash_logo_img);
        text_logo = findViewById(R.id.textView_logo_splash);

        fade = AnimationUtils.loadAnimation(this,R.anim.fade_anim);
        slide = AnimationUtils.loadAnimation(this,R.anim.slide_anim);
    }
}