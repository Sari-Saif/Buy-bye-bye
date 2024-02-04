package com.example.buy_bye_bye_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActiveItemAdapter extends RecyclerView.Adapter<ActiveItemAdapter.MyViewHolder> {

    Context context;

    ArrayList<ActiveOrderItem> list;

    String storename;

    public ActiveItemAdapter(Context context, ArrayList<ActiveOrderItem> list, String storename) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.storename = storename;
    }


    @NonNull
    @Override
    public ActiveItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.active_order_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveItemAdapter.MyViewHolder holder, int position) {
        ActiveOrderItem activeOrderItem = list.get(position);

        holder.product.setText(activeOrderItem.getName());
        holder.amount.setText(activeOrderItem.getAmount());
        holder.price.setText(activeOrderItem.getPrice());

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Stores")
                .child(this.storename)
                .child("Products")
                .child(activeOrderItem.getName());

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String imageUrl = snapshot.child("image").getValue(String.class);
                    // Load the image into the ImageView using Picasso
                    Picasso.get().load(imageUrl).into(holder.image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView product;
        TextView amount;
        TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ivProductImage);
            product = itemView.findViewById(R.id.tvProductName);
            amount = itemView.findViewById(R.id.tvAmount);
            price = itemView.findViewById(R.id.tvPrice);
        }
    }
}
