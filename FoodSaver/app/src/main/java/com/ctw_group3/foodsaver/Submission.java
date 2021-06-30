package com.ctw_group3.foodsaver;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Submission extends AppCompatActivity{
    EditText etName,etMobile,etSName, etAddress, etFoodName, etFoodDesc;
    Button btSubmit;
    AwesomeValidation awesomeValidation;
    private String TAG = "SubmissionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        // Ensure the user is signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // null means the user is not signed in
        if (user != null) {
            etName = findViewById(R.id.et_name);
            etMobile = findViewById(R.id.et_mobile);
            etSName = findViewById(R.id.et_store_name);
            etAddress = findViewById(R.id.et_address);
            etFoodName = findViewById(R.id.et_food_name);
            etFoodDesc = findViewById(R.id.et_food_desc);
            btSubmit = findViewById(R.id.bt_submit);

            // Validators for the EditBoxes
            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
            awesomeValidation.addValidation(this,R.id.et_name,
                    RegexTemplate.NOT_EMPTY,R.string.invalid_name);
            awesomeValidation.addValidation(this,R.id.et_mobile
                    ,"[5-9]{1}[0-9]{9}$",R.string.invalid_number);
            awesomeValidation.addValidation(this,R.id.et_store_name
                    ,RegexTemplate.NOT_EMPTY,R.string.invalid_storename);

            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (awesomeValidation.validate()){
                        Toast.makeText(getApplicationContext()
                                ,"Validation Successful.",Toast.LENGTH_SHORT).show();
                        // Pull data from EditBoxes
                        String name = etName.getText().toString();
                        String mobile = etMobile.getText().toString();
                        String storeName = etSName.getText().toString();
                        String address = etAddress.getText().toString();
                        String foodName = etFoodName.getText().toString();
                        String foodDesc = etFoodDesc.getText().toString();

                        // Map to hold the data pulled from EditBoxes
                        Map<String, Object> submission = new HashMap<>();
                        submission.put("ownerName", name);
                        submission.put("mobile", mobile);
                        submission.put("storeName", storeName);
                        submission.put("address", address);
                        submission.put("foodName", foodName);
                        submission.put("foodDesc", foodDesc);

                        // Access FireStore instance
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        // Add map object to the submissions collection
                        db.collection("submissions")
                                .add(submission)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapShot added with ID: " + documentReference.get());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Validation Failed.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // User is not signed in, redirect to home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }
}
