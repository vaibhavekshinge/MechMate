package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class HomePage extends AppCompatActivity {
    TextView greetmsg;
    ImageView menubar, findmec, buyacces, bookslot, mecregister;

    FusedLocationProviderClient fusedLocationProviderClient;

    private final static int REQUEST_CODE = 100;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences preferences = getSharedPreferences("mec_prefs", MODE_PRIVATE);
//        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
//
//        if (isLoggedIn) {
//            // User is authenticated, navigate to the home page
//            startActivity(new Intent(getApplicationContext(), MechanicHomePage.class));
//        }
//        else {
//            // User is not authenticated, navigate to sign-in or sign-up
//            startActivity(new Intent(getApplicationContext(), HomePage.class));
//        }
//        finish();

        setContentView(R.layout.activity_home_page);


        greetmsg = findViewById(R.id.Greetmsg);
        menubar = findViewById(R.id.menubar);
        findmec = findViewById(R.id.findmec);
        buyacces = findViewById(R.id.buyacces);
        bookslot = findViewById(R.id.bookslot);
        mecregister = findViewById(R.id.mecregister);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        getLastLocation();

        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+5:30"); // Adjust the time zone as needed
        calendar.setTimeZone(timeZone);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);


//         Determine the greeting message based on the time
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

        buyacces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), BuyAccessories.class);
                startActivity(intent3);
            }
        });
        bookslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), book_a_slot.class);
                startActivity(intent4);
            }
        });
    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null){
                                Geocoder geocoder = new Geocoder(HomePage.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
        }
        else{
            askPermission();
        }
    }

    private void askPermission() {

        ActivityCompat.requestPermissions(HomePage.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else{
                Toast.makeText(this, "Please provide the required permission", Toast.LENGTH_SHORT).show();
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}



