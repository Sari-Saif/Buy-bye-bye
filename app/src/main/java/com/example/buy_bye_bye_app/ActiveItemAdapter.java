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
/**
 * Adapter for displaying active order items in a RecyclerView.
 * This adapter manages items of orders, including product name, amount, and price,
 * and dynamically loads product images from Firebase.
 */
public class ActiveItemAdapter extends RecyclerView.Adapter<ActiveItemAdapter.MyViewHolder> {

    // Context for inflating layout views
    Context context;
    // List of active order items to be displayed
    ArrayList<ActiveOrderItem> list;
    // Store name to query for product images
    String storename;

    /**
     * this function updates the list of items in case of filter by text
     * @param filteredList new list to show
     */
    public void setFilteredList(ArrayList<ActiveOrderItem> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Constructor for ActiveItemAdapter.
     *
     * @param context   The current context (activity) the adapter is being used in.
     * @param list      List of ActiveOrderItem objects to display.
     * @param storename The name of the store, used to retrieve product images from Firebase.
     */
    public ActiveItemAdapter(Context context, ArrayList<ActiveOrderItem> list, String storename) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.storename = storename;
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
    public ActiveItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.active_order_item, parent, false);
        return new MyViewHolder(v);
    }
    /**
     * Binds the data for an active order item to the ViewHolder.
     * This includes setting text views for product name, amount, and price,
     * and loading the product image from Firebase using Picasso.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ActiveItemAdapter.MyViewHolder holder, int position) {
        ActiveOrderItem activeOrderItem = list.get(position);
        // Set product details
        holder.product.setText(activeOrderItem.getName());
        holder.amount.setText(activeOrderItem.getAmount() + " pieces");
        holder.price.setText(activeOrderItem.getPrice() + "$");
        // Firebase reference to retrieve product image
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Stores")
                .child(this.storename)
                .child("Products")
                .child(activeOrderItem.getName());
        // Load product image from Firebase
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
     * Contains views for product image, name, amount, and price.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView product;
        TextView amount;
        TextView price;
        /**
         * Constructor for MyViewHolder.
         *
         * @param itemView The View that you inflated in onCreateViewHolder.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            image = itemView.findViewById(R.id.ivProductImage);
            product = itemView.findViewById(R.id.tvProductName);
            amount = itemView.findViewById(R.id.tvAmount);
            price = itemView.findViewById(R.id.tvPrice);
        }
    }
}
