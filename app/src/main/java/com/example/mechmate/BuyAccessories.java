package com.example.mechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyAccessories extends AppCompatActivity {
    ImageView backacces, mirrorimg, hornimg, tyreimg, wheelimg, toolkitimg, seatimg, helmetimg;
    TextView mirrortxt, horntxt, tyretxt, wheeltxt, toolkittxt, seattxt, helmettxt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_accessories);
        backacces = findViewById(R.id.backacces);
        mirrorimg = findViewById(R.id.mirimg);
        mirrortxt = findViewById(R.id.mirtxt);
        hornimg = findViewById(R.id.hornimg);
        horntxt = findViewById(R.id.horntxt);
        tyreimg = findViewById(R.id.tirimg);
        tyretxt = findViewById(R.id.tirtxt);
        wheelimg = findViewById(R.id.wlimg);
        wheeltxt = findViewById(R.id.wltxt);
        toolkitimg = findViewById(R.id.toolimg);
        toolkittxt = findViewById(R.id.tooltxt);
        seatimg = findViewById(R.id.seatimg);
        seattxt = findViewById(R.id.seattxt);
        helmetimg = findViewById(R.id.helmetimg);
        helmettxt = findViewById(R.id.helmettxt);

        backacces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);

                }

            });

        }

        public void MirrorAmazon(View view){
            Uri uri = Uri.parse("https://www.amazon.in/s?k=vehicle+mirror&crid=21K2S65S1CMB7&sprefix=vechile+mirr%2Caps%2C464&ref=nb_sb_ss_ts-doa-p_5_12");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        public void hornAmazon(View view){
            Uri uri1 = Uri.parse("https://www.amazon.in/s?k=vehicle+horns&ref=nb_sb_noss_2");
            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
            startActivity(intent1);
        }
        public void tyreAmazon(View view){
            Uri uri2 = Uri.parse("https://www.amazon.in/s?k=vehicle+tyre&crid=381SNXIQ6IWUN&sprefix=vehicle+tyre%2Caps%2C188&ref=nb_sb_ss_ts-doa-p_4_12");
            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
            startActivity(intent2);
        }
        public void wheelAmazon(View view){
            Uri uri3 = Uri.parse("https://www.amazon.in/s?k=vehicle+alloy+wheel&ref=nb_sb_noss_2");
            Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
            startActivity(intent3);
        }
        public void toolkitAmazon(View view){
            Uri uri4 = Uri.parse("https://www.amazon.in/s?k=vehicle+tool+kit+set&ref=nb_sb_ss_ts-doa-p_2_12");
            Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
            startActivity(intent4);
        }
        public void seatAmazon(View view){
            Uri uri5 = Uri.parse("https://www.amazon.in/s?k=vehicle+seat&ref=nb_sb_noss_1");
            Intent intent5 = new Intent(Intent.ACTION_VIEW, uri5);
            startActivity(intent5);
        }
        public void helmetAmazon(View view){
            Uri uri6 = Uri.parse("https://www.amazon.in/s?k=helmet&ref=nb_sb_noss_1");
            Intent intent6 = new Intent(Intent.ACTION_VIEW, uri6);
            startActivity(intent6);
        }


    }

