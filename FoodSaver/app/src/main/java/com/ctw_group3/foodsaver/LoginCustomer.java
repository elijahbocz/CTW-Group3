package com.ctw_group3.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginCustomer extends AppCompatActivity {
    // Shared instance of FirebaseAuth object
    private FirebaseAuth mAuth;
    private String TAG = "LoginCustomer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincustomer);

        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        // Pull email and password from EditTexts
        EditText emailInput = findViewById(R.id.LoginEmailInput);
        String email = emailInput.getText().toString();
        EditText passwordInput = findViewById(R.id.LoginPasswordInput);
        String password = passwordInput.getText().toString();

        // User login
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            loadPosts();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            TextView loginError = findViewById(R.id.LoginError);
                            loginError.setText("Invalid login");
                        }
                    }
                });
    }

    public void loadPosts() {
        Intent intent = new Intent(this, Customer.class);
        startActivity(intent);
    }
}