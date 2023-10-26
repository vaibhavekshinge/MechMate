package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class dashboard extends AppCompatActivity {

    ImageView profile, appstatus, settings, help, legal, goback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        profile = findViewById(R.id.profile);
        appstatus = findViewById(R.id.appointmentstatus);
        settings = findViewById(R.id.settings);
        help = findViewById(R.id.help);
        legal = findViewById(R.id.legal);
        goback = findViewById(R.id.goback);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
    }
}