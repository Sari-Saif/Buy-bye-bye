package com.example.buy_bye_bye_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.MyViewHolder> {

    Context context;

    ArrayList<ActiveOrder> list;

    public ActiveAdapter(Context context, ArrayList<ActiveOrder> list) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
    }


    @NonNull
    @Override
    public ActiveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.active_order, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveAdapter.MyViewHolder holder, int position) {
        ActiveOrder activeOrder = list.get(position);
        holder.storename.setText(activeOrder.getStoreName());
        holder.orderid.setText(activeOrder.getOrderID());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getContext().toString().contains("S7@")) {
                    Intent intent = new Intent(v.getContext(), S8.class);
                    intent.putExtra("store_name", activeOrder.getStoreName());
                    intent.putExtra("customer_name", activeOrder.getCustomerName());
                    intent.putExtra("order_id", activeOrder.getOrderID());
                    v.getContext().startActivity(intent);
                } else if(v.getContext().toString().contains("S6@")) {
                    Intent intent = new Intent(v.getContext(), S10.class);
                    intent.putExtra("store_name", activeOrder.getStoreName());
                    intent.putExtra("customer_name", activeOrder.getCustomerName());
                    intent.putExtra("order_id", activeOrder.getOrderID());
                    v.getContext().startActivity(intent);
                }
                else if(v.getContext().toString().contains("C7@")) {
                    Intent intent = new Intent(v.getContext(), C9.class);
                    intent.putExtra("store_name", activeOrder.getStoreName());
                    intent.putExtra("order_id", activeOrder.getOrderID());
                    v.getContext().startActivity(intent);
                }else{

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storename;
        TextView orderid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            storename = itemView.findViewById(R.id.tvStoreName);
            orderid = itemView.findViewById(R.id.tvOrderID);
        }
    }
}
