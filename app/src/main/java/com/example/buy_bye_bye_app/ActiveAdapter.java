package com.example.buy_bye_bye_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * Adapter for displaying active orders in a RecyclerView.
 */
public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.MyViewHolder> {

    // Context for inflating layout and starting new activities
    Context context;
    // List of active orders to display
    ArrayList<ActiveOrder> list;

    /**
     * this function updates the list of items in case of filter by text
     * @param filteredList new list to show
     */
    public void setFilteredList(ArrayList<ActiveOrder> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Constructor for ActiveAdapter.
     *
     * @param context Context for layout inflation and intent creation.
     * @param list    List of ActiveOrder objects to display. If null, initializes an empty list.
     */
    public ActiveAdapter(Context context, ArrayList<ActiveOrder> list) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
    }
    /**
     * Callback interface for asynchronous data retrieval.
     * Callback interface to handle asynchronous data retrieval
     */
    public interface FirebaseCallback {
        void onCallback(ArrayList<String> storeNames);
    }
    /**
     * Inflates the layout for an item and returns a new ViewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new instance of MyViewHolder containing the inflated view.
     */
    @NonNull
    @Override
    public ActiveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.active_order, parent, false);
        return new MyViewHolder(v);
    }
    /**
     * Binds the data for an active order to the ViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ActiveAdapter.MyViewHolder holder, int position) {
        ActiveOrder activeOrder = list.get(position);
        holder.storename.setText(activeOrder.getStoreName());
        holder.orderid.setText(activeOrder.getOrderID());

        // Set click listener for the itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate based on context's name
                if(v.getContext().toString().contains("S7@"))
                {
                    Intent intent = new Intent(v.getContext(), S8.class);
                    intent.putExtra("store_name", activeOrder.getStoreName());
                    intent.putExtra("customer_name", activeOrder.getCustomerName());
                    intent.putExtra("order_id", activeOrder.getOrderID());
                    v.getContext().startActivity(intent);
                }
                else if(v.getContext().toString().contains("S6@"))
                {
                    Intent intent = new Intent(v.getContext(), S10.class);
                    intent.putExtra("store_name", activeOrder.getStoreName());
                    intent.putExtra("customer_name", activeOrder.getCustomerName());
                    intent.putExtra("order_id", activeOrder.getOrderID());
                    v.getContext().startActivity(intent);
                }
                else if (v.getContext().toString().contains("C6@"))
                {
                    Intent intent = new Intent(v.getContext(), C8.class);
                    intent.putExtra("store_name", activeOrder.getStoreName());
                    intent.putExtra("customer_name", activeOrder.getCustomerName());
                    intent.putExtra("order_id", activeOrder.getOrderID());
                    v.getContext().startActivity(intent);
                }
                else if(v.getContext().toString().contains("C7@"))
                {

                }
                else
                {

                }

            }
        });
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter's data set.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     * ViewHolder class for recycling ItemViews in the RecyclerView.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storename;
        TextView orderid;
        /**
         * Constructor for MyViewHolder.
         *
         * @param itemView The View that you inflated in onCreateViewHolder.
         */
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            storename = itemView.findViewById(R.id.tvStoreName);
            orderid = itemView.findViewById(R.id.tvOrderID);
        }
    }
}
