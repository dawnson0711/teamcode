package com.example.groupasg;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private TextView app_name, app_slogan;
    private ImageView app_logo_icon;
    private View top_view_stripe1, top_view_stripe2, top_view_stripe3;
    private View bottom_view_stripe1, bottom_view_stripe2, bottom_view_stripe3;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide Navigation Bar and make activity displayed in full screen
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_splash_screen);
        // Finding the splash screen elements view
        app_name = findViewById(R.id.app_name);
        app_slogan = findViewById(R.id.app_slogan);
        app_logo_icon = findViewById(R.id.app_logo);

        top_view_stripe1 = findViewById(R.id.top_view_stripe1);
        top_view_stripe2 = findViewById(R.id.top_view_stripe2);
        top_view_stripe3 = findViewById(R.id.top_view_stripe3);

        bottom_view_stripe1 = findViewById(R.id.bottom_view_stripe1);
        bottom_view_stripe2 = findViewById(R.id.bottom_view_stripe2);
        bottom_view_stripe3 = findViewById(R.id.bottom_view_stripe3);

        // Initialization of anim file resource
        Animation logoIconAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_animation);
        Animation appNameAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_animation);

        Animation topViewStripe1Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_view_animation);
        Animation topViewStripe2Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_view_animation);
        Animation topViewStripe3Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_view_animation);

        Animation bottomViewStripe1Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_view_animation);
        Animation bottomViewStripe2Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_view_animation);
        Animation bottomViewStripe3Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_view_animation);

        // Start first animation effect
        top_view_stripe1.startAnimation(topViewStripe1Animation);
        bottom_view_stripe1.startAnimation(bottomViewStripe1Animation);

        topViewStripe1Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                top_view_stripe2.setVisibility(View.VISIBLE);
                bottom_view_stripe2.setVisibility(View.VISIBLE);

                top_view_stripe2.startAnimation(topViewStripe2Animation);
                bottom_view_stripe2.startAnimation(bottomViewStripe2Animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // Continue second animation effect after first animation
        topViewStripe2Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                top_view_stripe3.setVisibility(View.VISIBLE);
                bottom_view_stripe3.setVisibility(View.VISIBLE);

                top_view_stripe3.startAnimation(topViewStripe3Animation);
                bottom_view_stripe3.startAnimation(bottomViewStripe3Animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // Continue third animation effect after second animation
        topViewStripe3Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                app_logo_icon.setVisibility(View.VISIBLE);
                app_logo_icon.startAnimation(logoIconAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logoIconAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                app_name.setVisibility(View.VISIBLE);
                app_name.startAnimation(appNameAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        appNameAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                app_slogan.setVisibility(View.VISIBLE);
                final String animationText = app_slogan.getText().toString();
                app_slogan.setText("");
                count = 0;

                // set text to display one by one according the length
                new CountDownTimer(animationText.length() * 101L, 100) {
                    @Override
                    public void onTick(long l) {
                        app_slogan.setText(app_slogan.getText().toString() + animationText.charAt(count));
                        count++;
                    }

                    @Override
                    public void onFinish() {
                        // *Insert Login Page or Main Page In Here
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}