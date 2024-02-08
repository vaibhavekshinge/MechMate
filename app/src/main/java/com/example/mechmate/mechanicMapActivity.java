package com.example.mechmate;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ZeroResultsException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mechanicMapActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    TextView cusname, cusvechileno, cusmodel, cusdistance, cusprblm;
    ImageView reject, accept;
    double cuslat, cuslon;

    double meclat, meclon;

    private LatLng customerlocation;
    private LatLng meclatLng;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_map);
        cusname = findViewById(R.id.cusname);
        cusvechileno = findViewById(R.id.cusvechileno);
        cusmodel = findViewById(R.id.cusmodel);
        cusdistance = findViewById(R.id.cusdistance);
        cusprblm = findViewById(R.id.cusprblm);

        cusname.setText(getIntent().getStringExtra("selectedCusName"));
        cusvechileno.setText(getIntent().getStringExtra("selectedCusVechileNo"));
        cusmodel.setText(getIntent().getStringExtra("selectedCusVechiletype") + ", " + getIntent().getStringExtra("selectedCusModel"));
        cusprblm.setText(getIntent().getStringExtra("selectedCusPrblm"));

        cuslat = Double.parseDouble(getIntent().getStringExtra("selectedCusLat"));
        cuslon = Double.parseDouble(getIntent().getStringExtra("selectedCusLon"));

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(mechanicMapActivity.this, MechanicHomePage.class);
                startActivity(intent5);
                finish();
            }
        });


        // Obtain the SupportMapFragment and request map ready callback
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mechanicmap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        // Create a LocationCallback to receive updates
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                // Get the last location from the LocationResult
//                Location location = locationResult.getLastLocation();
//                if (location != null) {
//                    // Update your map with the new location
//                    fetchMyLocation();
//                }
//            }
//        };

    }

//    private void updateMapWithLocation(Location location) {
//        if (map != null) {
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            // Create a marker for the user's current location
//            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My Location");
//            map.clear(); // Clear existing markers if any
//            map.addMarker(markerOptions);
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//            map.animateCamera(cameraUpdate);
//        }
//    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        if(map != null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
//            map.getUiSettings().setZoomControlsEnabled(true);

            customerlocation = new LatLng(cuslat, cuslon);
            MarkerOptions marker = new MarkerOptions().position(customerlocation).title("Customer location").icon(BitmapDescriptorFactory.fromResource(R.drawable.mechaniclocation2));
            map.clear();
            map.addMarker(marker);

            fetchMyLocation();
            fetchRoute();
        }
    }



    private void fetchMyLocation() {
        if(map != null){
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
            Task<Location> task = fusedLocationProviderClient.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        meclat = location.getLatitude();
                        meclon = location.getLongitude();

                         meclatLng = new LatLng(meclat, meclon);

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(meclatLng)
                                .zoom(15)
                                .build();

                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        MarkerOptions markerOptions = new MarkerOptions().position(meclatLng).title("Your location").icon(BitmapDescriptorFactory.fromResource(R.drawable.mechaniclocation3));
//                        map.clear();
                        map.addMarker(markerOptions);
                    }

                }
            });
        }
    }

    private void fetchRoute() {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(getString(R.string.maps_api_key_new))
                .build();

        DirectionsApi.newRequest(geoApiContext)
                .origin(new com.google.maps.model.LatLng(meclat, meclon))
                .destination(new com.google.maps.model.LatLng(cuslat, cuslon))
                .mode(TravelMode.DRIVING) // Adjust travel mode as needed
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        if (result != null && result.routes.length > 0) {
                            if(result.routes != null && result.routes.length > 0){
                                Log.d(TAG, "DirectionsResult received");
                                drawPolyline(result.routes[0]);
                            }
                            else{
                                Log.e(TAG, "No routes found");
                            }
                        }
                        else {
                            Log.e(TAG, "DirectionsResult is null or empty");
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        // Handle errors here
                        if (e instanceof ZeroResultsException) {
                            Log.e(TAG, "No results found");
                            // Handle the case where no results are found
                        } else {
                            e.printStackTrace();
                            Log.e(TAG, "Directions API request failed", e);
                        }
                    }
                });
    }

    private void drawPolyline(DirectionsRoute route) {
        // Get the overview_polyline and add it to the map
        List<com.google.maps.model.LatLng> decodedPath = route.overviewPolyline.decodePath();
        if(!decodedPath.isEmpty()){
            Log.d(TAG, "Decoded path is not empty");
            List<LatLng> latLngPath = new ArrayList<>();
            for (com.google.maps.model.LatLng decodedLatLng : decodedPath) {
                latLngPath.add(new LatLng(decodedLatLng.lat, decodedLatLng.lng));
            }

            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(latLngPath)
                    .color(Color.BLACK)
                    .width(10);
            map.addPolyline(polylineOptions);
        }
        else{
            Log.e(TAG, "Decoded path is empty");
        }
    }
}