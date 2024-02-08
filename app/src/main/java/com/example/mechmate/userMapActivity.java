package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
//import android.location.LocationRequest;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    ImageView callmechanic;

    TextView mechanicname, mechanicdist;

    FirebaseDatabase db;
    DatabaseReference reference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        callmechanic = findViewById(R.id.callmecahanic);
        mechanicname = findViewById(R.id.mechanicname);
        mechanicdist = findViewById(R.id.distance);



        FirebaseApp.initializeApp(this);

        // Obtain the SupportMapFragment and request map ready callback
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Create a LocationCallback to receive updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // Get the last location from the LocationResult
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    // Update your map with the new location
                    updateMapWithLocation(location);
                }
            }
        };

        // Check and request location permission if needed
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String customerId = getIntent().getStringExtra("vechileno");

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Customers").child(customerId);
        reference.removeValue();

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
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
                    updateLocation(location.getLatitude(), location.getLongitude());
                }
            }
        }, getMainLooper());
    }

    // updating database with current latitude and longitude
    private void updateLocation(double latitude, double longitude) {

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Customers").child(getIntent().getStringExtra("vechileno"));
        reference.child("customerLatitude").setValue(latitude);
        reference.child("customerLongitude").setValue(longitude);
    }

    // Update the map with the new location
    private void updateMapWithLocation(Location location) {
        if (map != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // Create a marker for the user's current location
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My Location");
            map.clear(); // Clear existing markers if any
            map.addMarker(markerOptions);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Enable My Location layer if possible
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            retriveMechanicLocations();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop location updates when the activity stops
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void retriveMechanicLocations(){
        DatabaseReference mecref = FirebaseDatabase.getInstance().getReference("Mechanics");

        mecref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mechanicsnapshot : snapshot.getChildren()){
                    Mechanics mechanics = mechanicsnapshot.getValue(Mechanics.class);
                    if(mechanics != null){
                        double latitude = mechanics.meclatitude;
                        double longitude = mechanics.meclongitude;
                        String name = mechanics.mecnames;

                        displayMechanicOnMap(latitude, longitude, name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayMechanicOnMap(double mechanicLatitude, double mechanicLongitude, String mecname) {
        if (map != null) {
            LatLng mechanicLatLng = new LatLng(mechanicLatitude, mechanicLongitude);
            // Create a marker for each mechanic's location
            MarkerOptions markerOptions = new MarkerOptions().position(mechanicLatLng).title("Mechanic:" + mecname).icon(BitmapDescriptorFactory.fromResource(R.drawable.mechaniclocation3));
            map.addMarker(markerOptions);
        }
    }
}
