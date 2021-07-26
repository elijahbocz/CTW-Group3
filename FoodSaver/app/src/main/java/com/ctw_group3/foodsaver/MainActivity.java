package com.ctw_group3.foodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userselection);
    }

    public void loadRC(View view) {
        Intent intent = new Intent(this, RegistrationCustomer.class);
        startActivity(intent);
    }

    public void loadRFO(View view) {
        Intent intent = new Intent(this, RegistrationFoodOwner.class);
        startActivity(intent);
    }

//    public void loadRegistration(View view) {
//        Intent intent = new Intent(this, RegistrationFoodOwner.class);
//        startActivity(intent);
//    }
//
//    public void loadLogin(View view) {
//        Intent intent = new Intent(this, LoginFoodOwner.class);
//        startActivity(intent);
//    }
//
//    public void loadPosts(View view) {
//        Intent intent = new Intent(this, Customer.class);
//        startActivity(intent);
//    }
}