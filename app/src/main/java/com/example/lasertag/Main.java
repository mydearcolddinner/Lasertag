package com.example.lasertag;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {
    protected void onStart() {
        super.onStart();
        Intent i;
        i = new Intent(this, RecievingRadioPackage.class);
        startActivity(i);
    }
}
