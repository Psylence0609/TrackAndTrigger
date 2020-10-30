package com.example.trackandtrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth=FirebaseAuth.getInstance();
        CurrentUser=fAuth.getCurrentUser();

        if(CurrentUser == null)
        {
            Intent RegisterIntent = new Intent(MainActivity.this,register.class);
            startActivity(RegisterIntent);
        }
        else{
            Intent DashboardIntent = new Intent(MainActivity.this,Dashboard.class);
            startActivity(DashboardIntent);

        }
    }


}