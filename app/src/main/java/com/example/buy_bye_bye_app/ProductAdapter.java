package com.example.buy_bye_bye_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myViewHolder> {

    Context context;
    ArrayList<Product> ProductsList;
    String imgURL;
    String storename;


    public ProductAdapter(Context context, ArrayList<Product> productsList, String storename) {
        this.context = context;
        ProductsList = productsList;
        this.storename = storename;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.c4_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        //holder.Image.setText(ProductsList.get(position).getImage());
        String new_price = ProductsList.get(position).getPrice() + "$";
        String new_quantity = ProductsList.get(position).getQuantity() + " pieces";
        Picasso.get().load(ProductsList.get(position).getImage()).into(holder.Image);
        holder.Name.setText(ProductsList.get(position).getName());
        holder.Price.setText(new_price);
        holder.Quantity.setText(new_quantity);


        // Set a click listener to handle item clicks
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent
                Intent intent = new Intent(v.getContext(), S4_pop.class);

                // Put extras into the Intent
                intent.putExtra("name", ProductsList.get(position).getName());
                intent.putExtra("price", new_price);
                intent.putExtra("quantity", new_quantity);

                // Put the image URL as an extra
                intent.putExtra("imageURL", ProductsList.get(position).getImage());
                intent.putExtra("store_name", storename);

                // Start the activity
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductsList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView Image;
        TextView Name;
        TextView Price;
        TextView Quantity;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.C4_productImage);
            Name = (TextView) itemView.findViewById(R.id.C4_productName);
            Price = (TextView) itemView.findViewById(R.id.C4_productPrice);
            Quantity = (TextView) itemView.findViewById(R.id.C4_productQuantity);
        }
    }
}