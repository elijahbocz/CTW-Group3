package com.ctw_group3.foodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SubmissionsDetails extends AppCompatActivity {
    TextView t1,t2,t3, t4;
    ImageView idImg1;
    Button btn,btnc;

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
        idImg1.setImageResource(getIntent().getIntExtra("foodImageLink",0));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("I am here");
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                finish();
            }
        });
    }
}
