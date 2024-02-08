package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mecHomePagePopup extends AppCompatActivity {

    TextView CusName, CusVechileNo, CusModel, CusDist, CusPrblm;
    private static final int PERMISSION_REQUEST_SEND_SMS = 1;
    String CusNames, CusModels, CusPrblms, CusPhoneNo;
    ImageView AcceptReq, RejectReq;
    Double CusLat, CusLon;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mec_home_page_popup);
        CusName = findViewById(R.id.CusName);
        CusVechileNo = findViewById(R.id.CusVechileNo);
        CusModel = findViewById(R.id.CusModel);
        CusDist = findViewById(R.id.CusDist);
        CusPrblm = findViewById(R.id.CusPrblm);
        AcceptReq = findViewById(R.id.AcceptReq);
        RejectReq = findViewById(R.id.RejectReq);
        CusName.setText(getIntent().getStringExtra("selectedCusName"));
        CusVechileNo.setText(getIntent().getStringExtra("selectedCusVechileNo"));
        CusModel.setText(getIntent().getStringExtra("selectedCusVechiletype") + ", " + getIntent().getStringExtra("selectedCusModel"));
        CusPrblm.setText(getIntent().getStringExtra("selectedCusPrblm"));
        CusDist.setText(getIntent().getStringExtra("selectedCusDist"));

        CusNames = getIntent().getStringExtra("selectedCusName");
        CusPrblms = getIntent().getStringExtra("selectedCusPrblm");
        CusModels = getIntent().getStringExtra("selectedCusModel");
        CusPhoneNo = getIntent().getStringExtra("selectedCusPhone");

        CusLat = Double.parseDouble(getIntent().getStringExtra("selectedCusLat"));
        CusLon = Double.parseDouble(getIntent().getStringExtra("selectedCusLon"));

        RejectReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(mecHomePagePopup.this, MechanicHomePage.class);
                startActivity(intent5);
                finish();
            }
        });

        AcceptReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new DatabaseReference for the Connections node
                DatabaseReference connectionsRef = FirebaseDatabase.getInstance().getReference().child("Connections");

                // Get the mechanic's ID
                String mechanicId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Create a unique key for the connection
//                String connectionKey = connectionsRef.push().getKey();

                // Get the selected customer's ID
                String customerId = getIntent().getStringExtra("selectedCusVechileNo"); // Assuming you have a field for customer ID in the Customers node

                // Create a Connection object with relevant information
                Connections connection = new Connections(mechanicId, customerId);

                // Update the Connections node with the new connection
                connectionsRef.child(customerId).setValue(connection);

                // Remove the selected customer from the Customers node
                DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("Customers");
                customersRef.child(customerId).removeValue();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        //Send sms to mechanic
                        sendSMStoMechanic(getIntent().getStringExtra("mecPhoneNo"), CusNames, CusPhoneNo, CusPrblms, CusModels);
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
                    }
                }
                //Redirecting to Google maps
                String uri = "google.navigation:q=" + CusLat + "," + CusLon;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps"); // Specify the Google Maps package
                
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(mecHomePagePopup.this, "Google Maps not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendSMStoMechanic(String mecPhoneNo, String cusNames, String cusPhoneNo, String cusPrblms, String cusModels) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String smsMessage = "Your customer is waiting:\n"
                    + "Customer: " + cusNames + "\n"
                    + "Phone Number: " + cusPhoneNo + "\n"
                    + "Model: " + cusModels + "\n"
                    + "Problem Description: " + cusPrblms;

            smsManager.sendTextMessage(mecPhoneNo, null, smsMessage, null, null);
            Toast.makeText(this, "SMS sent to the mechanic", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}