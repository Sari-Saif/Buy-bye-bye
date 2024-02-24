package com.example.buy_bye_bye_app;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for displaying a list of stores in a RecyclerView.
 * This adapter is responsible for handling the layout and interaction of each store item within the RecyclerView.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    // Context in which the adapter is being used
    private Context context;
    // List of Store objects to be displayed
    private ArrayList<Store> list;
    String store_name;

    /**
     * this function updates the list of items in case of filter by text
     * @param filteredList new list to show
     */
    public void setFilteredList(ArrayList<Store> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Constructor for the StoreAdapter.
     *
     * @param context The current context where the RecyclerView is used.
     * @param list An ArrayList of Store objects to be displayed by the RecyclerView.
     */
    public StoreAdapter(Context context, ArrayList<Store> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * Inflates the item layout and creates a ViewHolder for each item in the RecyclerView.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new MyViewHolder that holds a View of the given view type.
     */
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.store, parent, false);
        return new MyViewHolder(v);
    }

    /**
     * Binds the data to the views in the ViewHolder.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Store store = list.get(position);
        holder.StoreName.setText(store.getStoreName());

        int sum_of_ratings = Integer.parseInt(store.getSum_ratings());
        int total_raters = Integer.parseInt(store.getTotal_raters());

        double store_rate = 0.0;
        if(total_raters == 0) {
            holder.Rating.setText(String.format("%.1f", store_rate));
        } else {
            store_rate = (double) sum_of_ratings / total_raters;
            holder.Rating.setText(String.format("%.1f", store_rate));
        }



    }

    /**
     * Returns the size of the list that contains the items the RecyclerView will display.
     *
     * @return The number of items available for display.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder class that defines the view for each store item.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // TextView for displaying the store's name
        TextView StoreName;
        TextView Rating;

        public MyViewHolder(@NotNull View storeView) {
            super(storeView);

            // Initialize the TextView by finding it by its ID
            StoreName = storeView.findViewById(R.id.tvStoreName);
            Rating = storeView.findViewById(R.id.tvRating);

            // Set an OnClickListener for the entire store item view
            storeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Determine which activity to navigate to based on the context
                    Intent intent;
                    if(v.getContext().toString().contains("S3@")) {
                        // Navigate to the S4 activity if the context is S3@
                        intent = new Intent(v.getContext(), S4.class);
                    } else {
                        // Otherwise, navigate to the C4 activity
                        intent = new Intent(v.getContext(), C4.class);
                    }
                    // Add the store name as an extra to the intent
                    intent.putExtra("name" , StoreName.getText().toString());
                    // Start the activity
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
