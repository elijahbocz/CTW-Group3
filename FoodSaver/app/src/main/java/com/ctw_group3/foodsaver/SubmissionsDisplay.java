package com.ctw_group3.foodsaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SubmissionsDisplay extends AppCompatActivity {
    RecyclerView recview;
    ArrayList<SubmissionsModel> datalist;
    FirebaseFirestore db;
    SubmissionsHelperAdapter adapter;
    ImageView imageView1;
    Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions_display);
        recview = (RecyclerView)findViewById(R.id.recyclerView);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        adapter = new SubmissionsHelperAdapter(datalist);
        recview.setAdapter(adapter);
        imageView1 = (ImageView) findViewById(R.id.idImage);

        db = FirebaseFirestore.getInstance();
        db.collection("submissions").orderBy("foodName", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list)
                {
                    SubmissionsModel obj = d.toObject(SubmissionsModel.class);
                    obj.setDocId(d.getId());
                    if (obj.getClaimed() != true) {
                        datalist.add(obj);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}