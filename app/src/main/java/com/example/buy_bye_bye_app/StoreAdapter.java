package com.example.buy_bye_bye_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    Context context;

    ArrayList<Store> list;

    public StoreAdapter(Context context, ArrayList<Store> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.store, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Store store = list.get(position);
        holder.StoreName.setText(store.getStoreName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView StoreName;

        public MyViewHolder(@NotNull View storeView) {
            super(storeView);

            StoreName = storeView.findViewById(R.id.tvStoreName);
        }

    }
}
