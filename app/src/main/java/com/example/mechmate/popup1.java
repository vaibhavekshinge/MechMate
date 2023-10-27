package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class popup1 extends AppCompatActivity {
    ImageView mecnewregister, mecsignin, goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup1);
        mecnewregister = findViewById(R.id.mecnewregister);
        mecsignin = findViewById(R.id.mecsignin);
        goback = findViewById(R.id.goback1);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        mecsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), popup2.class);
                startActivity(intent1);
            }
        });

        mecnewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), MechanicRegistration.class);
                startActivity(intent2);
            }
        });
    }
}