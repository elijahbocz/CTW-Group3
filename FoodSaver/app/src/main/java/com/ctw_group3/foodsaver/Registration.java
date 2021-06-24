package com.ctw_group3.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    // Shared instance of FirebaseAuth object
    private FirebaseAuth mAuth;
    private String TAG = "Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(View view) {
        // Clear old errors if needed
        TextView emailError = findViewById(R.id.EmailError);
        emailError.setText("");
        TextView passwordError = findViewById(R.id.PasswordError);
        passwordError.setText("");

        // Pull email and password from EditTexts
        EditText emailInput = findViewById(R.id.EmailInput);
        String email = emailInput.getText().toString();
        EditText passwordInput = findViewById(R.id.PasswordInput);
        String password = passwordInput.getText().toString();
        EditText confirmPasswordInput = findViewById(R.id.ConfirmPasswordInput);
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (password.equals(confirmPassword)) {
            // Creates the user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                TextView emailError = findViewById(R.id.EmailError);
                                emailError.setText("Incorrect email format, or email is already in use");
                            }
                        }
                    });
        } else {
            passwordError = findViewById(R.id.PasswordError);
            passwordError.setText("Ensure both passwords are the same");
        }


    }
}