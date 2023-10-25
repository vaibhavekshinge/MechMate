package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class OTP2 extends AppCompatActivity {

    EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;
    String getOtpBackend;
    TextView mobNum, resendotp;
    Button submit;
    ProgressBar progressBar2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);

        inputOtp1 = findViewById(R.id.inputotp1);
        inputOtp2 = findViewById(R.id.inputotp2);
        inputOtp3 = findViewById(R.id.inputotp3);
        inputOtp4 = findViewById(R.id.inputotp4);
        inputOtp5 = findViewById(R.id.inputotp5);
        inputOtp6 = findViewById(R.id.inputotp6);
        mobNum = findViewById(R.id.mobNum);
        submit = findViewById(R.id.submit);
        resendotp = findViewById(R.id.resendotp);
        progressBar2 = findViewById(R.id.progressBar2);

        mobNum.setText(getIntent().getStringExtra("phoneno"));

        getOtpBackend = getIntent().getStringExtra("backendotp");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputOtp1.getText().toString().trim().isEmpty() && !inputOtp2.getText().toString().trim().isEmpty() && !inputOtp3.getText().toString().trim().isEmpty() && !inputOtp4.getText().toString().trim().isEmpty() && !inputOtp5.getText().toString().trim().isEmpty() && !inputOtp6.getText().toString().trim().isEmpty()){
                    String userEnteredOtp = inputOtp1.getText().toString()+
                            inputOtp2.getText().toString()+
                            inputOtp3.getText().toString()+
                            inputOtp4.getText().toString()+
                            inputOtp5.getText().toString()+
                            inputOtp6.getText().toString();

                    if(getOtpBackend != null){
                        progressBar2.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getOtpBackend,userEnteredOtp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar2.setVisibility(View.GONE);
                                        submit.setVisibility(View.VISIBLE);

                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(getApplicationContext(),HomePage.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(OTP2.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
//                    Toast.makeText(OTP2.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(OTP2.this, "Please enter all numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        numberOtpMove();

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                        .setPhoneNumber(getIntent().getStringExtra("phoneno"))
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(OTP2.this)
                        .setCallbacks(
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(OTP2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        getOtpBackend = newbackendotp;
                                        Toast.makeText(OTP2.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });
    }

    private void numberOtpMove() {
        inputOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}