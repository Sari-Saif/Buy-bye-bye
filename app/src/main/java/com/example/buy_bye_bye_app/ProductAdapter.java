package com.example.buy_bye_bye_app;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myViewHolder> {

    Context context;
    ArrayList<Product> ProductsList;

    public ProductAdapter(Context context, ArrayList<Product> productsList) {
        this.context = context;
        ProductsList = productsList;
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