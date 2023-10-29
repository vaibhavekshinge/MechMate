package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.TimeZone;


public class HomePage extends AppCompatActivity {
    TextView greetmsg;
    ImageView menubar, findmec, buyacces, bookslot, mecregister;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        greetmsg = findViewById(R.id.Greetmsg);
        menubar = findViewById(R.id.menubar);
        findmec = findViewById(R.id.findmec);
        buyacces = findViewById(R.id.buyacces);
        bookslot = findViewById(R.id.bookslot);
        mecregister = findViewById(R.id.mecregister);

        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+5:30"); // Adjust the time zone as needed
        calendar.setTimeZone(timeZone);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Determine the greeting message based on the time
        String greeting;
        if (hour >= 4 && hour < 12) {
            greeting = "Good morning, Welcome to MechMate!";
            greetmsg.setText(greeting);
        } else if (hour >= 12 && hour < 17) {
            greeting = "Good afternoon, Welcome to MechMate!";
            greetmsg.setText(greeting);
        } else if (hour >= 17 && hour < 21) {
            greeting = "Good evening, Welcome to MechMate!";
            greetmsg.setText(greeting);
        } else {
            greeting = "Seems like you are in problem!!";
            greetmsg.setText(greeting);
        }

        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });

        mecregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), popup1.class);
                startActivity(intent1);
            }
        });

        findmec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), FindMechanic.class);
                startActivity(intent2);
            }
        });
    }

}
