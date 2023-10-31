package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class popup2 extends AppCompatActivity {
    EditText email, password;
    ImageView goback, enter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        goback = findViewById(R.id.goback2);
        enter = findViewById(R.id.enter);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), popup1.class);
                startActivity(intent);
            }
        });
    }
}