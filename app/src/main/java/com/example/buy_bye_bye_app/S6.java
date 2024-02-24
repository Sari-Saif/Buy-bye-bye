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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * The S6 activity class extends AppCompatActivity and is designed to display the history of active orders
 * related to specific store names fetched from Firebase.
 */
public class S6 extends AppCompatActivity {

    // Firebase Authentication instance to manage user sessions
    private FirebaseAuth mAuth;
    // RecyclerView to display the list of active orders
    private RecyclerView rv;
    // ArrayList to hold the list of ActiveOrder objects fetched from Firebase
    private ArrayList<ActiveOrder> list;
    // DatabaseReference to Firebase to access the "Orders" node
    private DatabaseReference databaseReference;
    // Adapter for the RecyclerView to display ActiveOrder objects
    private ActiveAdapter adapter;
    // ArrayList to store names of stores to filter orders
    ArrayList<String> store_names;

    private SearchView search_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s6);

        // Retrieve the list of store names passed from the previous activity
        store_names = getIntent().getStringArrayListExtra("store_name_list");

        // setting the search bar for searching stores in list
        search_history = findViewById(R.id.search_history);
        search_history.clearFocus();
        search_history.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        mAuth = FirebaseAuth.getInstance();
        // Get a reference to the "Orders" node in Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        // Initialize the list of ActiveOrder objects

        list = new ArrayList<>();
        // Initialize the ActiveAdapter with the context and the list
        adapter = new ActiveAdapter(this, list);
        // Attach a ValueEventListener to fetch and listen for changes in the "History" child node
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the existing list to avoid duplicate entries on data change
                for(DataSnapshot x : snapshot.child("History").getChildren()) {
                    ActiveOrder y = x.getValue(ActiveOrder.class);
                    // Add the order to the list if it matches one of the store names
                    if(store_names.contains(y.getStoreName())) {
                        list.add(y);
                    }
                }
                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Initialize the RecyclerView
        rv = findViewById(R.id.show_history);
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
     * Navigates back to the S3 activity.
     */
    public void cancel(View view) {
        Intent i = new Intent(S6.this, S3.class);
        startActivity(i);
    }
    /**
     * Navigates to the S9 activity for editing profile, passing along the list of store names.
     */
    public void edit_profile(View view) {
        Intent i = new Intent(S6.this, S9.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
        finish();
    }
    /**
     * Navigates to the S7 activity for viewing new orders, passing along the list of store names.
     */
    public void new_orders(View view) {
        Intent i = new Intent(S6.this, S7.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
        finish();
    }

    /**
     * Signs out the current user from Firebase and navigates back to the MainActivity.
     * Clears the activity stack to prevent returning to the sign-in screen upon pressing back.
     */
    public void exit(View view) {
        mAuth.signOut();

        Intent i = new Intent(S6.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}