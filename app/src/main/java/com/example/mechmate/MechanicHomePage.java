package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MechanicHomePage extends AppCompatActivity {

    TextView ShopName;

    String MecPhoneNo;
    ImageView backmhp;
    RecyclerView recyclerView;
    ArrayList<Customers> customersArrayList;
    MyAdapter myAdapter;
    DatabaseReference reference;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    double meclat=0, meclon=0;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_home_page);
        ShopName = findViewById(R.id.garage);
        backmhp = findViewById(R.id.backmhp);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference("Customers");
        customersArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, customersArrayList, meclat, meclon);

        mAuth = FirebaseAuth.getInstance();
        
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               customersArrayList.clear();

               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   Customers customers = dataSnapshot.getValue(Customers.class);
                   customersArrayList.add(customers);
               }
               myAdapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here if needed
                // You can also start a new activity here if you don't want to handle it in MyAdapter
                Customers selectedCustomer = customersArrayList.get(position);
                String selectedCusName = selectedCustomer.customernames;
                String selectedCusVechiletype = selectedCustomer.types;
                String selectedCusModel = selectedCustomer.models;
                String selectedCusVechileNo = selectedCustomer.vechilenos;
                String selectedCusPrblm = selectedCustomer.prblmdescs;
                String selectedCusLat = String.valueOf(selectedCustomer.customerLatitude);
                String selectedCusLon = String.valueOf(selectedCustomer.customerLongitude);
                String selectedCusPhoneNo = selectedCustomer.customerphones;

                double customerDistance = calculateDistance(meclat, meclon, selectedCustomer.customerLatitude, selectedCustomer.customerLongitude);
                String scustomerDistance = String.format(Locale.getDefault(), "Approx. %.2f km", customerDistance);

                Intent intent2 = new Intent(MechanicHomePage.this, mecHomePagePopup.class);
                intent2.putExtra("selectedCusName", selectedCusName);
                intent2.putExtra("selectedCusVechiletype", selectedCusVechiletype);
                intent2.putExtra("selectedCusModel", selectedCusModel);
                intent2.putExtra("selectedCusVechileNo", selectedCusVechileNo);
                intent2.putExtra("selectedCusPrblm", selectedCusPrblm);
                intent2.putExtra("selectedCusLat", selectedCusLat);
                intent2.putExtra("selectedCusLon", selectedCusLon);
                intent2.putExtra("selectedCusDist", scustomerDistance);
                intent2.putExtra("selectedCusPhone", selectedCusPhoneNo);
                intent2.putExtra("mecPhoneNo", MecPhoneNo);
                startActivity(intent2);
            }
        });


        backmhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Toast.makeText(MechanicHomePage.this, "Logged out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);  // Set the flag to false on sign-out
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Check and request location permission if needed
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            startLocationUpdates();
        }

        // Setting the Shop name
        FirebaseUser currentmechanic = FirebaseAuth.getInstance().getCurrentUser();
        if(currentmechanic != null){
            String mechanicid = currentmechanic.getUid();
            DatabaseReference mecref = FirebaseDatabase.getInstance().getReference().child("Mechanics").child(mechanicid);

            mecref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String shopname = snapshot.child("shopnames").getValue(String.class);
                    ShopName.setText(shopname);
                    MecPhoneNo = snapshot.child("phones").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MechanicHomePage.this, "Unable to read Shop name", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    // Start requesting location updates at a specific interval
    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000); // Update location every 5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    // Update the customer location in Firebase Database
                    meclat = location.getLatitude();
                    meclon = location.getLongitude();
                    updateLocation(location.getLatitude(), location.getLongitude());

                    myAdapter.setMechanicLocation(meclat, meclon);
                }
            }
        }, getMainLooper());
    }

    private void updateLocation(double latitude, double longitude) {
        FirebaseUser currentmechanic = FirebaseAuth.getInstance().getCurrentUser();
        if(currentmechanic != null){
            String mechanicid = currentmechanic.getUid();
            DatabaseReference mecref = FirebaseDatabase.getInstance().getReference().child("Mechanics").child(mechanicid);

            mecref.child("meclatitude").setValue(latitude);
            mecref.child("meclongitude").setValue(longitude);
        }
//        else{
//            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mAuth.signOut();
        Toast.makeText(MechanicHomePage.this, "Logged out", Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", false);  // Set the flag to false on sign-out
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        startActivity(intent);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        final int R = 6371; // Radius of the Earth in kilometers
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return R * c; // Distance in kilometers

        float[] results = new  float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        double distance = results[0];
        return  distance/1000;

    }
}