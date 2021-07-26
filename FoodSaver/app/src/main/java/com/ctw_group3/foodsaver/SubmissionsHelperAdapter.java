package com.ctw_group3.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SubmissionsHelperAdapter extends RecyclerView.Adapter<SubmissionsHelperAdapter.myviewholder> {
    public SubmissionsHelperAdapter(ArrayList<SubmissionsModel> datalist) {
        this.datalist = datalist;
    }

    ArrayList<SubmissionsModel> datalist;

    @NonNull
    @Override
    public SubmissionsHelperAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submissions_singlerow,parent,false
        );
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final  myviewholder holder, final int position) {
        holder.t1.setText(datalist.get(position).getfoodName());
        holder.t2.setText(datalist.get(position).getFoodDesc());
        holder.t3.setText(datalist.get(position).getMobile());
        holder.t4.setText(datalist.get(position).getAddress());

        Glide.with(holder.img.getContext()).load(datalist.get(position).getFoodImageLink()).into(holder.img);

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.t1.getContext(), SubmissionsDetails.class);
                intent.putExtra("foodDec", datalist.get(position).getFoodDesc());
                intent.putExtra("foodName", datalist.get(position).getfoodName());
                intent.putExtra("mobile", datalist.get(position).getMobile());
                intent.putExtra("address", datalist.get(position).getAddress());
                intent.putExtra("foodImageLink", datalist.get(position).getFoodImageLink());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t1.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4;
        ImageView img;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2) ;
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            img = (ImageView)itemView.findViewById(R.id.idImage);
        }
    }
}

