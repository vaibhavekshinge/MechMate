package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class markAsDone extends AppCompatActivity {

    Button markasdone;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_as_done);

        markasdone = findViewById(R.id.markAD);

        String cusPhoneNo = getIntent().getStringExtra("cusPhoneNo");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0); // Replace 0 with the notification ID you used when showing the notification

        markasdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getApplicationContext(), MechanicHomePage.class);
                startActivity(intent5);
                finish();

                DatabaseReference Connectionref = FirebaseDatabase.getInstance().getReference().child("Connections");
                Connectionref.child(cusPhoneNo).removeValue();
            }
        });
    }
}