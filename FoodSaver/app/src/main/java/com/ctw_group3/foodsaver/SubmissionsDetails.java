package com.ctw_group3.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class SubmissionsDetails extends AppCompatActivity {
    TextView t1,t2,t3, t4;
    ImageView idImg1;
    Button btn,btnc;
    private String TAG = "SubmissionsDetails";

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions_details);
        t1 = (TextView)findViewById(R.id.t11);
        t3 = (TextView)findViewById(R.id.Mobile1);
        t2 = (TextView)findViewById(R.id.FoodDec1);
        t4 = (TextView)findViewById(R.id.Address1);
        idImg1=(ImageView)findViewById(R.id.idImage1);
        btn = (Button)findViewById(R.id.btnback);
        btn = (Button)findViewById(R.id.btnback);
        btnc= (Button)findViewById(R.id.btnclaim);
        t2.setText(getIntent().getStringExtra("foodDec").toString());
        t1.setText(getIntent().getStringExtra("foodName").toString());
        t3.setText(getIntent().getStringExtra("mobile").toString());
        t4.setText(getIntent().getStringExtra("address").toString());
        Glide.with(this).load(getIntent().getStringExtra("foodImageLink")).into(idImg1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), SubmissionsDisplay.class));
                finish();
            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access FireStore instance
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                final DocumentReference submissionsDocRef = db.collection("submissions").document(getIntent().getStringExtra("docId"));
                db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        transaction.update(submissionsDocRef, "claimed", true);

                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Transaction success!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });
                loadCustomerActivity();

                finish();
            }
        });
    }

    public void loadCustomerActivity() {
        String foodDesc = t2.getText().toString();
        String foodName = t1.getText().toString();
        String mobile = t3.getText().toString();
        String address = t4.getText().toString();

        Intent intent = new Intent(this, Customer.class);
        intent.putExtra("foodDesc", foodDesc);
        intent.putExtra("foodName", foodName);
        intent.putExtra("mobile", mobile);
        intent.putExtra("address", address);

        startActivity(intent);
    }
}
