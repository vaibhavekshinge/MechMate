package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class FindMechanic extends AppCompatActivity {
    ImageView backfm, findmecbutton;
    EditText customername, type, model, vechileno, prblmdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mechanic);
        backfm = findViewById(R.id.backfm);
        findmecbutton = findViewById(R.id.findmecbutton);
        customername = findViewById(R.id.customername);
        type = findViewById(R.id.vehicletype);
        model = findViewById(R.id.model);
        vechileno = findViewById(R.id.vehicleno);
        prblmdesc = findViewById(R.id.prblmdesc);

        backfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

    }
}