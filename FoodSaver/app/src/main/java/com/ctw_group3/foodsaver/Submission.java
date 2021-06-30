package com.ctw_group3.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Submission extends AppCompatActivity{
    EditText etName,etMobile,etSName, etAddress, etFoodName, etFoodDesc;
    Button btSubmit, btUploadImage;
    ImageView foodImage;
    private String foodImageLink;

    AwesomeValidation awesomeValidation;
    private String TAG = "SubmissionActivity";
    private final static int IMAGE_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
            foodImage = findViewById(R.id.food_image);
            btUploadImage = findViewById(R.id.upload_image_btn);
            btSubmit = findViewById(R.id.bt_submit);

            // Validators for the EditBoxes
            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
            awesomeValidation.addValidation(this,R.id.et_name,
                    RegexTemplate.NOT_EMPTY,R.string.invalid_name);
            awesomeValidation.addValidation(this,R.id.et_mobile
                    ,"[5-9]{1}[0-9]{9}$",R.string.invalid_number);
            awesomeValidation.addValidation(this,R.id.et_store_name
                    ,RegexTemplate.NOT_EMPTY,R.string.invalid_storename);

            btUploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_CODE);
                }
            });

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
                        submission.put("foodImageLink", foodImageLink);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Compare the resultCode with the IMAGE_CODE constant
            if (requestCode == IMAGE_CODE) {
                // Get the uri of the image from data
                // URI is basically pointing to the image stored on the android device
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Update the preview image in the layout
                    foodImage.setImageURI(selectedImageUri);
                    // Resize the image to 250x250
                    foodImage.getLayoutParams().height = 250;
                    foodImage.getLayoutParams().width = 250;

                    try {
                        // Convert the image pointed at by the URI into a bitmap, then into an array of bytes
                        // Goal is to convert the image to base64
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        // Resize the image
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        byte[] byteArray = outputStream.toByteArray();

                        // Convert the byte array to a Base64 String
                        String imageEncodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        // Create a body for the HTTP POST request
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("image", imageEncodedString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Perform a HTTP POST request using the Volley library to Imgur
                        String url ="https://api.imgur.com/3/upload/";
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(this);
                        // The HTTP request will return a JSON Object
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.w(TAG, response.toString());
                                // Store the link of the image's URL
                                try {
                                    // The link is nested inside of an object called data
                                    JSONObject data =  response.getJSONObject("data");
                                    // Access the link in the data object and retrieve the link
                                    foodImageLink = data.getString("link");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                onBackPressed();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                // Headers for the POST request, Imgur requires authorization
                                final Map<String, String> headers = new HashMap<>();
                                headers.put("Authorization", "Client-ID dad1b3ce85b1e3f");
                                return headers;
                            }
                        };

                        // Add the request to the RequestQueue.
                        queue.add(jsonRequest);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
