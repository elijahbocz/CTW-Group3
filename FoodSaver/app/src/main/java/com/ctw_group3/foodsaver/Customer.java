package com.ctw_group3.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Customer extends AppCompatActivity {
    // Shared instance of FirebaseAuth object
    private TextView foodName, foodDesc, mobile, address, customerEmail;
    private FirebaseAuth mAuth;
    private String TAG = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        mAuth = FirebaseAuth.getInstance();

        // Ensure the user is signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            customerEmail = findViewById(R.id.CustomerEmail);
            customerEmail.setText(userEmail);

            foodName = findViewById(R.id.ROFoodNameValue);
            foodDesc = findViewById(R.id.ROFoodDescValue);
            mobile = findViewById(R.id.ROMobileValue);
            address = findViewById(R.id.ROAddressValue);

            foodName.setText(getIntent().getStringExtra("foodName"));
            foodDesc.setText(getIntent().getStringExtra("foodDesc"));
            mobile.setText(getIntent().getStringExtra("mobile"));
            address.setText(getIntent().getStringExtra("address"));
        } else {
            Intent intent = new Intent(this, LoginCustomer.class);
            startActivity(intent);
        }
    }
}