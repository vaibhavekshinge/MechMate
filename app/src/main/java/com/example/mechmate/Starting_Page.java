package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Starting_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);
    }

    public void openOtp1Activity(View view){
        Intent intent = new Intent(this, OTP1.class);
        startActivity(intent);
    }

}