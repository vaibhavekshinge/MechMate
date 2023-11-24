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

                        if(passwordtoregisters.equals(confirmpasswords)){

                            if(phonenos.length() >=10){
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
                            }
                            else{
                                Toast.makeText(MechanicRegistration.this, "Please enter valid Phone No.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MechanicRegistration.this, "Password doest match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MechanicRegistration.this, "Please fill all the above fields", Toast.LENGTH_LONG).show();
                    }
            }
        });
    }
}