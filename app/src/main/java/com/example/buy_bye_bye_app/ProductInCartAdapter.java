package com.example.buy_bye_bye_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * Adapter class for a RecyclerView to display products that have been added to the cart.
 * This adapter handles the layout and binding of product data to views for display within a RecyclerView.
 */
public class ProductInCartAdapter extends RecyclerView.Adapter<ProductInCartAdapter.myViewHolder> {

    // Context in which the adapter is being used
    Context context;

   // List of ProductInCart objects to be displayed
    ArrayList<ProductInCart> ProductsInCartList;

    /**
     * this function updates the list of items in case of filter by text
     * @param filteredList new list to show
     */
    public void setFilteredList(ArrayList<ProductInCart> filteredList) {
        this.ProductsInCartList = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Constructor for the ProductInCartAdapter.
     *
     * @param context The current context.
     * @param productsInCartList The data list (products in cart) that the adapter will manage and display.
     */
    public ProductInCartAdapter(Context context, ArrayList<ProductInCart> productsInCartList) {
        this.context = context;
        ProductsInCartList = productsInCartList;
    }

    /**
     * Called when RecyclerView needs a new {@link myViewHolder} of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View for an item of the RecyclerView.
     */
    @NonNull
    @Override
    public ProductInCartAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the item layout from XML
        View view = LayoutInflater.from(context).inflate(R.layout.c5_item, parent, false);
        return new ProductInCartAdapter.myViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ProductInCartAdapter.myViewHolder holder, int position) {

        // Set the product name, price, and amount on the corresponding TextViews
        holder.Name.setText(ProductsInCartList.get(position).getName());
        holder.Price.setText("Unit Price: " + ProductsInCartList.get(position).getPrice() + "$");
        holder.Amount.setText("Amount: " + ProductsInCartList.get(position).getAmount());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the ProductsInCartList.
     */
    @Override
    public int getItemCount() {
        return ProductsInCartList.size();
    }

    /**
     * ViewHolder class that defines the view for each item in the data set.
     */
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView Amount;
        TextView Price; // per unit

        /**
         * Constructor for the ViewHolder, references TextViews from the item layout.
         *
         * @param itemView The View that you inflated in {@link #onCreateViewHolder(ViewGroup, int)}.
         */
       public myViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find and store references to the TextViews in the layout
            Name = (TextView) itemView.findViewById(R.id.C5_item_productName);
            Amount = (TextView) itemView.findViewById(R.id.C5_item_productAmount);
            Price = (TextView) itemView.findViewById(R.id.C5_item_productUnitPrice);
        }
    }
}
