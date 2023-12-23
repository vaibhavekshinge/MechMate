package com.example.mechmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class FindMechanic extends AppCompatActivity {
    ImageView backfm, findmecbutton;
    EditText customername, type, model, vechileno, prblmdesc;
    String customernames, types, models, vechilenos, prblmdescs;

    FirebaseDatabase db;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mechanic);
        backfm = findViewById(R.id.backfm);
        findmecbutton = findViewById(R.id.findmecbutton);
        customername = findViewById(R.id.customername);
        type = findViewById(R.id.vehicletype);
        model = findViewById(R.id.model);
        vechileno = findViewById(R.id.vehicleno);
        prblmdesc = findViewById(R.id.prblmdesc);

        backfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        findmecbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customernames = customername.getText().toString();
                types = type.getText().toString();
                models = model.getText().toString();
                vechilenos = vechileno.getText().toString();
                prblmdescs = prblmdesc.getText().toString();


                if(!customernames.isEmpty() && !types.isEmpty() && !models.isEmpty() && !vechilenos.isEmpty() && !prblmdescs.isEmpty()){

                    Customers customers = new Customers(customernames, types, models, vechilenos, prblmdescs);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Customers");

                    reference.child(vechilenos).setValue(customers).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Intent intent2 = new Intent(getApplicationContext(), userMapActivity.class);
                            startActivity(intent2);
                        }
                    });
                }
                else{
                    Toast.makeText(FindMechanic.this, "Please fill all the above fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}