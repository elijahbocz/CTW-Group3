package com.ctw_group3.foodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            TextView displayUserEmail = findViewById(R.id.DisplayUserEmail);
            String email = user.getEmail();
            displayUserEmail.setText(email);
            TextView displayUserUID = findViewById(R.id.DisplayUserUID);
            String uid = user.getUid();
            displayUserUID.setText(uid);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void loadSubmission(View view){
        Intent intent = new Intent(this, Submission.class);
        startActivity(intent);
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}