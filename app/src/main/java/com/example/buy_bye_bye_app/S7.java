package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * The S7 class extends AppCompatActivity and manages a RecyclerView to display a list of active orders.
 * This activity is part of an application that likely handles order processing or management.
 */
public class S7 extends AppCompatActivity {

    // RecyclerView for displaying the list of active orders.
    private RecyclerView rv;
    // ArrayList to hold the active orders fetched from Firebase.
    private ArrayList<ActiveOrder> list;
    // DatabaseReference to Firebase to access the Orders data.
    DatabaseReference databaseReference;
    // Custom adapter for the RecyclerView to display ActiveOrder objects.
    ActiveAdapter adapter;
    // ArrayList to store names of stores, used to filter orders.
    ArrayList<String> store_names;

    private SearchView search_active;

    /**
     * onCreate is called when the activity is starting.
     * It initializes the activity, sets up the RecyclerView and its adapter, and starts listening to Firebase for data changes.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);

        // Retrieves a list of store names passed from the previous activity.
        store_names = getIntent().getStringArrayListExtra("store_name_list");

        // setting the search bar for searching stores in list
        search_active = findViewById(R.id.search_active);
        search_active.clearFocus();
        search_active.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        // Gets a reference to the "Orders" node in the Firebase database.
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        // Initializes the list to store active orders.
        list = new ArrayList<>();

        // Initializes the adapter for the RecyclerView with the context and the list of orders.
        adapter = new ActiveAdapter(this, list);

        // Sets a ValueEventListener on the database reference to listen for changes in the data.
        databaseReference.addValueEventListener(new ValueEventListener() {

            /**
             * Called when there is a change in the data at this location. It is also called each time this listener is attached.
             * @param snapshot The current data at the location
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clears the existing list to avoid duplicate entries.
                list.clear();
                // Iterates over the children of the "Active" node, filtering by store name and adding them to the list.
                for(DataSnapshot x : snapshot.child("Active").getChildren()) {
                    ActiveOrder y = x.getValue(ActiveOrder.class);
                    if(store_names.contains(y.getStoreName())) {
                        list.add(y);
                    }
                }
                // Notifies the adapter that the data set has changed, causing the RecyclerView to refresh.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Initializes the RecyclerView and sets its layout manager and adapter.
        rv = findViewById(R.id.recyclerView2);
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
     * Handles the "cancel" action, intended to navigate the user back to the S6 activity,
     * potentially passing back any necessary data.
     * @param view The view that was clicked.
     */
    public void cancel(View view) {
        // Creates an intent to start the S6 activity, passing along the store names list.
        Intent i = new Intent(S7.this, S6.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
    }
}