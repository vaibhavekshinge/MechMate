package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MechanicHomePage extends AppCompatActivity {

    TextView ShopName;
    ImageView backmhp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_home_page);
        ShopName = findViewById(R.id.garage);
        backmhp = findViewById(R.id.backmhp);

        String shopname = getIntent().getStringExtra("shopname");
        ShopName.setText(shopname);

        backmhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MechanicHomePage.this, "Logged out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);  // Set the flag to false on sign-out
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
    }
}