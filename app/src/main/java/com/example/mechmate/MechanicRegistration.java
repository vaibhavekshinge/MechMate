package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MechanicRegistration extends AppCompatActivity {
    ImageView back, submit;
    EditText mecname, shoploc, adharno, shopname, mobno;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration);
        back = findViewById(R.id.back);
        submit = findViewById(R.id.submit1);
        mecname = findViewById(R.id.mecname);
        shoploc = findViewById(R.id.shoploc);
        adharno = findViewById(R.id.adhar);
        shopname = findViewById(R.id.shopname);
        mobno = findViewById(R.id.mobno);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), popup1.class);
                startActivity(intent);
            }
        });
    }
}