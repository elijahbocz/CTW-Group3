package com.ctw_group3.foodsaver;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


public class Submission extends AppCompatActivity{
EditText etName,etMobile,etSName,etEmail, etWebsite;
Button btSubmit;

AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);




        etName = findViewById(R.id.editText2);
        etMobile = findViewById(R.id.et_mobile);
        etSName = findViewById(R.id.et_store_name);
        etEmail = findViewById(R.id.et_email);
        etWebsite = findViewById(R.id.et_website);
        btSubmit = findViewById(R.id.bt_submit);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.editText2,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        awesomeValidation.addValidation(this,R.id.et_mobile
        ,"[5-9]{1}[0-9]{9}$",R.string.invalid_number);

        awesomeValidation.addValidation(this,R.id.et_store_name
                ,RegexTemplate.NOT_EMPTY,R.string.invalid_storename);
        awesomeValidation.addValidation(this,R.id.et_email
        , Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        awesomeValidation.addValidation(this,R.id.et_website
        ,Patterns.WEB_URL,R.string.invalid_website);

        btSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext()
                    ,"Validation Successful.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Validation Failed.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loadUser() {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }
}
