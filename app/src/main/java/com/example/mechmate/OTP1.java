package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP1 extends AppCompatActivity {

    EditText phoneno;
    Button getotp;

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp1);

        phoneno = findViewById(R.id.phoneno);
        getotp = findViewById(R.id.getotp);
        progress = findViewById(R.id.progressBar);


        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneno.getText().toString().trim().isEmpty() == false){
                    if(phoneno.getText().toString().trim().length() >= 10){

                        progress.setVisibility(View.VISIBLE);
                        getotp.setVisibility(View.INVISIBLE);

                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                                .setPhoneNumber(phoneno.getText().toString())
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(OTP1.this)
                                .setCallbacks(
                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                progress.setVisibility(View.GONE);
                                                getotp.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                progress.setVisibility(View.GONE);
                                                getotp.setVisibility(View.VISIBLE);
                                                Toast.makeText(OTP1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                progress.setVisibility(View.GONE);
                                                getotp.setVisibility(View.VISIBLE);
                                                Intent intent = new Intent(getApplicationContext(), OTP2.class);
                                                intent.putExtra("phoneno", phoneno.getText().toString());
                                                intent.putExtra("backendotp", backendotp);
                                                startActivity(intent);
                                            }
                                        }
                                )
                                .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);

//                        PhoneAuthProvider.verifyPhoneNumber(
//                                phoneno.getText().toString(),
//                                60,
//                                TimeUnit.SECONDS,
//                                OTP1.this,
//                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                    @Override
//                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                        progress.setVisibility(View.GONE);
//                                        getotp.setVisibility(View.VISIBLE);
//                                    }
//
//                                    @Override
//                                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                                        progress.setVisibility(View.GONE);
//                                        getotp.setVisibility(View.VISIBLE);
//                                        Toast.makeText(OTP1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                        progress.setVisibility(View.GONE);
//                                        getotp.setVisibility(View.VISIBLE);
//                                        Intent intent = new Intent(getApplicationContext(), OTP2.class);
//                                        intent.putExtra("phoneno", phoneno.getText().toString());
//                                        intent.putExtra("backendotp", backendotp);
//                                        startActivity(intent);
//                                    }
//                                }
//                        );
                    }
                    else{
                        Toast.makeText(OTP1.this, "Enter vaild mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(OTP1.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}