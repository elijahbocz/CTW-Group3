package com.ctw_group3.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSelection extends AppCompatActivity {
    // Shared instance of FirebaseAuth object
    private FirebaseAuth mAuth;
    private String TAG = "UserSelection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userselection);

        mAuth = FirebaseAuth.getInstance();
//        Button FoodOwnerButton = findViewById(R.id.FOButton);
//        Button CustomerButton = findViewById(R.id.CButton);
//
//        FoodOwnerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadRFO();
//            }
//        });
//
//        CustomerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadRC();
//            }
//        });
    }

    public void loadRC(View view) {
        Intent intent = new Intent(this, RegistrationCustomer.class);
        startActivity(intent);
    }

    public void loadRFO(View view) {
        Intent intent = new Intent(this, RegistrationFoodOwner.class);
        startActivity(intent);
    }
}