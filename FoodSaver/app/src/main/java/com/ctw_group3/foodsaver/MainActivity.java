package com.ctw_group3.foodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadRegistration(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void loadLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void loadPosts(View view) {
        Intent intent = new Intent(this, Posts.class);
        startActivity(intent);
    }

    public void loadSubmission(View view){
        Intent intent = new Intent(this, Submission.class);
        startActivity(intent);
    }
}