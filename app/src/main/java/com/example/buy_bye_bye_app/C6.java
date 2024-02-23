package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Activity for displaying the history of active orders associated with the current user.
 */
public class C6 extends AppCompatActivity {

    private FirebaseAuth mAuth;// Firebase Authentication instance for user authentication
    private RecyclerView rv;// RecyclerView for displaying the list of active orders
    private ArrayList<ActiveOrder> list;// ArrayList to hold active orders
    private DatabaseReference databaseReference;// Reference to the database for retrieving orders
    private ActiveAdapter adapter;// Adapter for the RecyclerView
    private String currentUserEmail = null; // Email of the currently logged in user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c6); // Set the content view to the activity's layout

        // Initialize buttons and their click listeners for navigation and actions
        move_to_orders_pending_window();// Navigate to the orders pending window

        // Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        // Get a reference to the "Orders/History" node in Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders/History");

        // Initialize the list and adapter for active orders
        list = new ArrayList<>();
        // Get the current user's email if they are logged in
        adapter = new ActiveAdapter(this, list);

        // Get the current user's email
        if (mAuth.getCurrentUser() != null) {
            currentUserEmail = mAuth.getCurrentUser().getEmail();
        }
        // Listen for changes in the database to retrieve active orders
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                list.clear();// Clear the list to avoid duplicate entries
                for(DataSnapshot orderSnapshot : snapshot.getChildren())
                {
                    ActiveOrder order = orderSnapshot.getValue(ActiveOrder.class);
                    // Add the order to the list if it belongs to the current user
                    if (order != null && order.getOrderID() != null && !order.getOrderID().isEmpty() && order.getCustomerName() != null &&
                            order.getCustomerName().equals(currentUserEmail))
                    {
                        // Check if the order has any products before adding it to the list
                        list.add(order);
                    }
                }
                // Notify the adapter that the data set has changed to refresh the list view
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });

        // Setup RecyclerView with a LinearLayoutManager and the adapter
        rv = findViewById(R.id.show_history_C);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    /**
     * Navigates back to the previous activity when the back button is pressed.
     */
    public void back(View view) {
        Intent i = new Intent(C6.this, C3.class);
        startActivity(i);
    }
    /**
     * Navigates to the orders pending window.
     */
    private void move_to_orders_pending_window(){
        Button orders_pending = (Button) findViewById(R.id.C6_OrdersPending_button);
        orders_pending.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C6.this, C7.class));
            }
        });
    }

    /**
     * Navigates to the edit profile activity.
     */
    public void edit_profile(View view) {
        Intent i = new Intent(C6.this, C9.class);// Intent to navigate to C9 activity
        startActivity(i);
    }

    /**
     * Signs out the current user and exits to the main activity.
     */
    public void exit(View view) {
        mAuth.signOut();// Sign out the current user

        Intent i = new Intent(C6.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();// Finish the current activity
    }
}