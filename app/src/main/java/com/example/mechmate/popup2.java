package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class popup2 extends AppCompatActivity {
    EditText enteredemail, enteredpassword;
    ImageView goback, enter;

    DatabaseReference reference;

    private FirebaseAuth mAuth;

    public static String PREFS_NAME = "MyPrefsFile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);
        enteredemail = findViewById(R.id.mecloginemail);
        enteredpassword= findViewById(R.id.password);
        goback = findViewById(R.id.goback2);
        enter = findViewById(R.id.enter);

        mAuth = FirebaseAuth.getInstance();

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), popup1.class);
                startActivity(intent);
                finish();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = enteredemail.getText().toString();
                String password = enteredpassword.getText().toString();

                // Sign in the user with the provided email and password
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(popup2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    SharedPreferences preferences = getSharedPreferences(popup2.PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    // Redirect to the mechanic's home page or any desired screen
                                    Intent intent = new Intent(getApplicationContext(), MechanicHomePage.class);
                                    startActivity(intent);
                                    finish(); // Finish the current activity to prevent going back to it with the back button
                                } else {
                                    Exception exception = task.getException();
                                    if (exception != null) {
                                        Log.e("FirebaseAuth", "Authentication failed: " + exception.getMessage());
                                        Toast.makeText(popup2.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}