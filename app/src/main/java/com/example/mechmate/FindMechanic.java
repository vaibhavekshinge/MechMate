package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class FindMechanic extends AppCompatActivity {
    ImageView backfm, findmecbutton;
    EditText customername, type, model, vechileno, prblmdesc, customerphone;
    String customernames, types, models, vechilenos, prblmdescs, customerphones;

    double customerLatitude, customerLongitude;

    FirebaseDatabase db;

    DatabaseReference reference;

    // FusedLocationProviderClient for location updates
    private FusedLocationProviderClient fusedLocationClient;


    @SuppressLint("MissingInflatedId")
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
        customerphone = findViewById(R.id.customerphone);

        requestCurrentLocation();

        backfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        findmecbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customernames = customername.getText().toString();
                types = type.getText().toString();
                models = model.getText().toString();
                vechilenos = vechileno.getText().toString();
                prblmdescs = prblmdesc.getText().toString();
                customerphones = customerphone.getText().toString();


                if(!customernames.isEmpty() && !types.isEmpty() && !models.isEmpty() && !vechilenos.isEmpty() && !prblmdescs.isEmpty() && !customerphones.isEmpty() && customerphones.length() >= 10){

                    Customers customers = new Customers(customernames, types, models, vechilenos, prblmdescs, customerLatitude, customerLongitude, customerphones);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Customers");

                    reference.child(vechilenos).setValue(customers).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Intent intent2 = new Intent(getApplicationContext(), userMapActivity.class);
                            intent2.putExtra("vechileno",vechilenos);
                            startActivity(intent2);
                            finish();
                        }
                    });
                }
                else{
                    Toast.makeText(FindMechanic.this, "Please fill all the above fields / Check the phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void requestCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1002);
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {

                customerLatitude = location.getLatitude();
                customerLongitude = location.getLongitude();

                // Update the mechanic's location in Firebase Database
//                updateLocation(customerLatitude, customerLongitude);
            } else {
                // Handle the case where the location is not available
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }
}