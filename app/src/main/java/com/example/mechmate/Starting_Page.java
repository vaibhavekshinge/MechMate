package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class Starting_Page extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences HomePagepreferences = getSharedPreferences(OTP2.homePagePref, 0);
                boolean isVerified = HomePagepreferences.getBoolean("isVerified", false);

                if (isVerified) {
                    // User is authenticated, navigate to the home page
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                    finish();
                }
//                else {
//                    // User is not authenticated, navigate to sign-in or sign-up
//                    startActivity(new Intent(getApplicationContext(), HomePage.class));
//                    finish();
//                }
            }
        },SPLASH_TIME_OUT);
    }

    public void openOtp1Activity(View view){
        Intent intent = new Intent(this, OTP1.class);
        startActivity(intent);
    }

}