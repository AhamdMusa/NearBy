package com.example.nearby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3500;


    //Variables
    Animation logoAnim, textAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);


        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo);
        textAnim = AnimationUtils.loadAnimation(this, R.anim.text);


        image.setAnimation(logoAnim);
        logo.setAnimation(textAnim);


        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  },
                SPLASH_SCREEN);
    }
}
