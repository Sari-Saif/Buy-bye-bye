package com.example.buy_bye_bye_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Adapter class for handling the display of products in a RecyclerView.
 * This adapter is used to populate each item in the RecyclerView with product details.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myViewHolder> {

     Context context;// Context in which the RecyclerView is used
     ArrayList<Product> ProductsList;// List of Product objects to be displayed
     String imgURL;
     String storename;// Name of the store, used for passing to intents

    /**
     * this function updates the list of items in case of filter by text
     * @param filteredList new list to show
     */
    public void setFilteredList(ArrayList<Product> filteredList) {
        this.ProductsList = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Constructor for the ProductAdapter.
     *
     * @param context Context in which the adapter is being used.
     * @param productsList List of Product objects to be displayed.
     * @param storename Name of the store.
     */
    public  ProductAdapter(Context context, ArrayList<Product> productsList, String storename) {
        this.context = context;
        ProductsList = productsList;
        this.storename = storename;
    }

    /**
     * Called when RecyclerView needs a new {@link myViewHolder} of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("ABC", "START: " + parent.getContext().toString() + " :END");
        // Determine the layout to inflate based on the context
        if(parent.getContext().toString().contains("S4@")) {
            View view = LayoutInflater.from(context).inflate(R.layout.s4_item, parent, false);
            return new myViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.c4_item, parent, false);
            return new myViewHolder(view);
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        // Set product details
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

                if (v.getContext().toString().contains("S4@")) {
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
                } else {
                    Intent intent = new Intent(v.getContext(), C4_pop_up.class);
                    // Put extras into the Intent
                    intent.putExtra("product_name", ProductsList.get(position).getName());
                    intent.putExtra("product_price", new_price);
                    intent.putExtra("quantity", new_quantity);

                    // Put the image URL as an extra
                    //intent.putExtra("imageURL", ProductsList.get(position).getImage());
                    intent.putExtra("store_name", storename);

                    // Start the activity
                    v.getContext().startActivity(intent);
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
        return ProductsList.size();
    }
    /**
     * ViewHolder class that holds the view for each product item.
     */
    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView Image; // ImageView for the product's image
        TextView Name;// TextView for the product's name
        TextView Price;// TextView for the product's price
        TextView Quantity;// TextView for the product's quantity

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            Image = itemView.findViewById(R.id.C4_productImage);
            Name = (TextView) itemView.findViewById(R.id.C4_productName);
            Price = (TextView) itemView.findViewById(R.id.C4_productPrice);
            Quantity = (TextView) itemView.findViewById(R.id.C4_productQuantity);
        }
    }
}
