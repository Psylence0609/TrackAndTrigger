package com.example.trackandtrigger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PhoneNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        String Name = getIntent().getStringExtra("Name");
        String mail = getIntent().getStringExtra("EmailID");

    }
}