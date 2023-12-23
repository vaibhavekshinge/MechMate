package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class MechanicRegistration extends AppCompatActivity {
    ImageView back, submit;
    EditText mecname, shoploc, adharno, shopname, phoneno, passwordtoregister, confirmpassword;

    String mecnames, shoplocs, adharnos, shopnames, phonenos, passwordtoregisters, confirmpasswords;

    FirebaseDatabase db;
    DatabaseReference reference;


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
        phoneno = findViewById(R.id.phonenum);
        passwordtoregister = findViewById(R.id.passwordtoregister);
        confirmpassword = findViewById(R.id.confirmpasswordtoregister);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), popup1.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mecnames = mecname.getText().toString();
                shoplocs = shoploc.getText().toString();
                adharnos = adharno.getText().toString();
                shopnames = shopname.getText().toString();
                phonenos = phoneno.getText().toString();
                passwordtoregisters = passwordtoregister.getText().toString();
                confirmpasswords = confirmpassword.getText().toString();

                if (!mecnames.isEmpty() && !shoplocs.isEmpty() && !adharnos.isEmpty() && !shopnames.isEmpty() && !phonenos.isEmpty() && !passwordtoregisters.isEmpty() && !confirmpasswords.isEmpty()) {

                    if (passwordtoregisters.equals(confirmpasswords)) {

                        if (phonenos.length() >= 10) {
                            Mechanics mechanics = new Mechanics(mecnames, shoplocs, adharnos, shopnames, phonenos, passwordtoregisters, confirmpasswords);
                            db = FirebaseDatabase.getInstance();
                            reference = db.getReference("Mechanics");
                            reference.child(phonenos).setValue(mechanics).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MechanicRegistration.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                                    SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("isLoggedIn", true);  // Set the flag to true after sign-in or sign-up
                                    editor.apply();

                                    Intent intent2 = new Intent(getApplicationContext(), MechanicHomePage.class);
                                    intent2.putExtra("shopname", shopnames);
                                    startActivity(intent2);
                                }
                            });
                        } else {
                            Toast.makeText(MechanicRegistration.this, "Please enter valid Phone No.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MechanicRegistration.this, "Password doest match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MechanicRegistration.this, "Please fill all the above fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

//package com.example.mechmate;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class MechanicRegistration extends AppCompatActivity {
//    ImageView back, submit;
//    EditText mecname, shoploc, adharno, shopname, phoneno, passwordtoregister, confirmpassword;
//
//    String mecnames, shoplocs, adharnos, shopnames, phonenos, passwordtoregisters, confirmpasswords;
//
//    double mechlatitude, mechlongitude;
//
//    FirebaseDatabase db;
//    DatabaseReference reference;
//
//    // FusedLocationProviderClient for location updates
//    private FusedLocationProviderClient fusedLocationClient;
//
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mechanic_registration);
//        back = findViewById(R.id.back);
//        submit = findViewById(R.id.submit1);
//        mecname = findViewById(R.id.mecname);
//        shoploc = findViewById(R.id.shoploc);
//        adharno = findViewById(R.id.adhar);
//        shopname = findViewById(R.id.shopname);
//        phoneno = findViewById(R.id.phonenum);
//        passwordtoregister = findViewById(R.id.passwordtoregister);
//        confirmpassword = findViewById(R.id.confirmpasswordtoregister);
//
//        requestCurrentLocation();
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), popup1.class);
//                startActivity(intent);
//            }
//        });
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mecnames = mecname.getText().toString();
//                shoplocs = shoploc.getText().toString();
//                adharnos = adharno.getText().toString();
//                shopnames = shopname.getText().toString();
//                phonenos = phoneno.getText().toString();
//                passwordtoregisters = passwordtoregister.getText().toString();
//                confirmpasswords = confirmpassword.getText().toString();
//
//                if (!mecnames.isEmpty() && !adharnos.isEmpty() && !shopnames.isEmpty() && !phonenos.isEmpty() && !passwordtoregisters.isEmpty() && !confirmpasswords.isEmpty()) {
//
//                    if (passwordtoregisters.equals(confirmpasswords)) {
//
//                        if (phonenos.length() >= 10) {
//                            Mechanics mechanics = new Mechanics(mecnames, shoplocs, adharnos, shopnames, phonenos, passwordtoregisters, confirmpasswords, mechlatitude, mechlongitude);
//                            db = FirebaseDatabase.getInstance();
//                            reference = db.getReference("Mechanics");
//                            reference.child(phonenos).setValue(mechanics).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(MechanicRegistration.this, "Registered Successfully", Toast.LENGTH_LONG).show();
//
//                                    SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME, 0);
//                                    SharedPreferences.Editor editor = preferences.edit();
//                                    editor.putBoolean("isLoggedIn", true);  // Set the flag to true after sign-in or sign-up
//                                    editor.apply();
//
//                                    Intent intent2 = new Intent(getApplicationContext(), MechanicHomePage.class);
//                                    intent2.putExtra("shopname", shopnames);
//                                    startActivity(intent2);
//                                }
//                            });
//
//                            // Start listening for location updates
//                            startLocationUpdates();
//                        } else {
//                            Toast.makeText(MechanicRegistration.this, "Please enter valid Phone No.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(MechanicRegistration.this, "Password doest match", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(MechanicRegistration.this, "Please fill all the above fields", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//
//    private void startLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create()
//                .setInterval(5000) // Update location every 5 seconds (adjust as needed)
//                .setFastestInterval(2000)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        // Request location updates
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                if (locationResult != null && locationResult.getLastLocation() != null) {
//                    Location location = locationResult.getLastLocation();
//                    // Update the mechanic's location in Firebase Database
//                    updateMechanicLocation(location.getLatitude(), location.getLongitude());
//                }
//            }
//        }, getMainLooper());
//    }
//
//    private void requestCurrentLocation() {
//        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
//            return;
//        }
//        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
//            if (location != null) {
//
//                mechlatitude = location.getLatitude();
//                mechlongitude = location.getLongitude();
//
//                // Update the mechanic's location in Firebase Database
//                updateMechanicLocation(mechlatitude, mechlongitude);
//            } else {
//                // Handle the case where the location is not available
//                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void updateMechanicLocation(double latitude, double longitude) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            String phonenos = user.getUid();
//            DatabaseReference mechanicRef = FirebaseDatabase.getInstance().getReference().child("Mechanics").child(phonenos);
//
//            mechanicRef.child("latitude").setValue(latitude);
//            mechanicRef.child("longitude").setValue(longitude);
//
//            Toast.makeText(this, "Location updated successfully", Toast.LENGTH_SHORT).show();
//        } else {
//            // Handle the case when the user is not authenticated
//            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}