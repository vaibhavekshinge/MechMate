package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
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


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
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

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0); // Replace 0 with the notification ID you used when showing the notification

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
                connectionsRef.child(CusPhoneNo).setValue(connection);

                // Remove the selected customer from the Customers node
                DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("Customers");
                customersRef.child(customerId).removeValue();

                sendNotification(CusNames, CusPhoneNo, CusPrblms, CusModels);

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

    public void sendNotification(String cusNames, String cusPhoneNo, String cusPrblms, String cusModels) {
        String channelId = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Your customer is waiting")
                .setContentText("Customer: " + cusNames)
                .setAutoCancel(false)  // Make the notification non-dismissable
                .setOngoing(true)      // Make the notification persistent
                .setPriority(Notification.PRIORITY_DEFAULT);

        // Create BigTextStyle to display long content
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Your customer is waiting")
                .bigText("Customer: " + cusNames + "\n" +
                        "Phone Number: " + cusPhoneNo + "\n" +
                        "Model: " + cusModels + "\n" +
                        "Problem Description: " + cusPrblms);
        builder.setStyle(bigTextStyle);

        // Create intents for actions
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + cusPhoneNo));
        PendingIntent callPendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent markAsDoneIntent = new Intent(this, markAsDone.class);
        markAsDoneIntent.putExtra("cusPhoneNo", cusPhoneNo);
        PendingIntent markAsDonePendingIntent = PendingIntent.getActivity(this, 0, markAsDoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Add actions to the notification
        builder.addAction(R.drawable.ic_call, "Call Customer", callPendingIntent)
                .addAction(R.drawable.ic_done, "Mark as Done", markAsDonePendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "MechMate Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications for MechMate app");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Unique ID for the notification
        int notificationId = 0;

        // Build the notification
        Notification notification = builder.build();

        // Show the notification
        notificationManager.notify(notificationId, notification);
    }

}