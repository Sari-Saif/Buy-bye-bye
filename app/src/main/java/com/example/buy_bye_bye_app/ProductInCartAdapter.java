package com.example.buy_bye_bye_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductInCartAdapter extends RecyclerView.Adapter<ProductInCartAdapter.myViewHolder> {

    Context context;
    ArrayList<ProductInCart> ProductsInCartList;

    @NonNull
    @Override
    public ProductInCartAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.c5_item, parent, false);
        return new ProductInCartAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInCartAdapter.myViewHolder holder, int position) {
        holder.Name.setText(ProductsInCartList.get(position).getName());
        holder.Price.setText("Unit Price: " + ProductsInCartList.get(position).getPrice() + "$");
        holder.Amount.setText("Amount: " + ProductsInCartList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return ProductsInCartList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView Amount;
        TextView Price; // per unit

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.C5_item_productName);
            Amount = (TextView) itemView.findViewById(R.id.C5_item_productAmount);
            Price = (TextView) itemView.findViewById(R.id.C5_item_productUnitPrice);
        }
    }
}
