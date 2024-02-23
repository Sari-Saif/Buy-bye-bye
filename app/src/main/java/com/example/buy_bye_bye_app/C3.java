package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Activity class C3 for displaying a list of stores in a RecyclerView.
 * Users can navigate to different profiles or sign out and return to the main activity.
 */
public class C3 extends AppCompatActivity {

    // RecyclerView for displaying the list of stores
    RecyclerView rv;
    // DatabaseReference for Firebase database access
    DatabaseReference db;
    // Adapter for the RecyclerView to display stores
    StoreAdapter adapter;
    // List of Store objects to be displayed
    ArrayList<Store> list;
    // FirebaseAuth instance for handling authentication
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c3);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Function calls to navigate to profile and retrieve store list
        move_to_C6_profile();
        retrive_store_list();
    }
    /**
     * Retrieves and displays a list of stores from Firebase.
     */
    private void retrive_store_list() {
        // Finding RecyclerView in the layout
        rv = findViewById(R.id.List);

        // Setting up Firebase database and reference to "Stores"
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("Stores");

        // Configuring RecyclerView with a LinearLayoutManager and fixed size
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Initializing the list and adapter for the RecyclerView
        list = new ArrayList<>();
        adapter = new StoreAdapter(this, list);
        rv.setAdapter(adapter);

        // Adding a ValueEventListener to fetch store data
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clearing the list for fresh data
                list.clear();
                for(DataSnapshot d : snapshot.getChildren()) {
                    // Converting the snapshot to a Store object
                    Store store = d.getValue(Store.class);
                    list.add(store);// Adding store to the list
                }
                adapter.notifyDataSetChanged();// Notifying the adapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * Sets up a click listener to navigate to the C6 activity when the image is clicked.
     */
    private void move_to_C6_profile() {
        ImageView to_c6 = (ImageView) findViewById(R.id.image_Button);
        to_c6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C3.this, C6.class));
            }
        });
    }

    /**
     * Signs out the current user and returns to the main activity.
     * @param view The view that was clicked to trigger this method.
     */
    public void ExitToMainActivity(View view) {
        mAuth.signOut();
        // Creating an intent to start the MainActivity
        Intent i = new Intent(C3.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();// Finishing the current activity
    }

}