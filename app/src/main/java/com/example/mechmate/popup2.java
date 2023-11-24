package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class popup2 extends AppCompatActivity {
    EditText enteredphoneno, enteredpassword;
    ImageView goback, enter;

    DatabaseReference reference;

    public static String PREFS_NAME = "MyPrefsFile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);
        enteredphoneno = findViewById(R.id.mecloginphone);
        enteredpassword= findViewById(R.id.password);
        goback = findViewById(R.id.goback2);
        enter = findViewById(R.id.enter);



        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), popup1.class);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno = enteredphoneno.getText().toString();
                String password = enteredpassword.getText().toString();
                if(!phoneno.isEmpty() && !password.isEmpty()){

                    if(phoneno.length() >= 10){
                        readData(phoneno, password);
                    }
                    else{
                        Toast.makeText(popup2.this, "Please enter valid Phone No.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(popup2.this, "Please enter Phone No./Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void readData(String phoneno, String password) {
        reference = FirebaseDatabase.getInstance().getReference("Mechanics");
        reference.child(phoneno).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {


                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String readedpassword = String.valueOf(dataSnapshot.child("passwordtoregisters").getValue());
                        String garagename = String.valueOf(dataSnapshot.child("shopnames").getValue());

                        if(Objects.equals(password, readedpassword)){

                            SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME,0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isLoggedIn", true);  // Set the flag to true after sign-in or sign-up
                            editor.apply();

                            Intent intent2 = new Intent(getApplicationContext(), MechanicHomePage.class);
                            intent2.putExtra("shopname", garagename);
                            startActivity(intent2);
                        }
                        else{
                            Toast.makeText(popup2.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(popup2.this, "Mechanic doesn't exist, Please Register yourself", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(popup2.this, "Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}