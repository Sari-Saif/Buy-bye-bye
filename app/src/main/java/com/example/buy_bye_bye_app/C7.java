package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * Activity for displaying active orders related to the currently logged-in user.
 */
public class C7 extends AppCompatActivity {

    private RecyclerView rv;// RecyclerView for displaying the list of active orders
    private ArrayList<ActiveOrder> list;// List to hold the active orders
    DatabaseReference databaseReference;// Database reference to the orders
    ActiveAdapter adapter; // Adapter for the RecyclerView
    private FirebaseAuth mAuth;// Firebase Authentication instance
    private String currentUserEmail = null;// Email of the currently logged-in user

    private SearchView search_pending_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c7);// Set the layout for the activity

        // setting the search bar for searching stores in list
        search_pending_orders = findViewById(R.id.search_pending_orders);
        search_pending_orders.clearFocus();
        search_pending_orders.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // Initialize Firebase Database reference to the "Orders" node
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        // Initialize the list and adapter
        list = new ArrayList<>();
        adapter = new ActiveAdapter(this, list);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Get the email of the currently logged-in user
        if (mAuth.getCurrentUser() != null) {
            currentUserEmail = mAuth.getCurrentUser().getEmail();
        }

        // Add a ValueEventListener to fetch active orders related to the user
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();// Clear the list to avoid duplicate entries
                for(DataSnapshot x : snapshot.child("Active").getChildren()) {

                    ActiveOrder order = x.getValue(ActiveOrder.class);
                    // Add the order to the list if it belongs to the current user
                    if (order != null && order.getOrderID() != null && !order.getOrderID().isEmpty() && order.getCustomerName() != null &&
                            order.getCustomerName().equals(currentUserEmail)) {
                        // Check if the order has any products before adding it to the list
                        list.add(order);
                    }
                }
                adapter.notifyDataSetChanged();// Notify the adapter to refresh the view
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Setup the RecyclerView with a LinearLayoutManager and the adapter
        rv = findViewById(R.id.recyclerView_c7);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    /**
     * this function filters the recycle view element
     * @param text the filter text
     */
    private void filterList(String text) {
        ArrayList<ActiveOrder> filteredList = new ArrayList<>();
        for(ActiveOrder item : list) {
            if(item.getStoreName().trim().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
            if(item.getOrderID().trim().toLowerCase().contains(text.toLowerCase())) {
                if(!filteredList.contains(item)) {
                    filteredList.add(item);
                }
            }
        }

        adapter.setFilteredList(filteredList);
    }

    /**
     * Navigates back to the C6 activity when the cancel button is pressed.
     */
    public void cancel(View view) {
        Intent i = new Intent(C7.this, C6.class);
        startActivity(i);
    }
    /**
     * Navigates back to the C6 activity when the back button is pressed.
     */
    public void back(View view) {
        Intent i = new Intent(C7.this, C6.class);
        startActivity(i);
    }
}