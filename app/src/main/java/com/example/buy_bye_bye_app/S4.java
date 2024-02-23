package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * Activity class for displaying a list of products within a selected store.
 * It allows users to view products, add new products, or cancel and return to the store list.
 */
public class S4 extends AppCompatActivity {

    // Name of the store selected by the user.
    private String store_name;

    // RecyclerView for displaying products.
    private RecyclerView rv;

    // List to hold Product objects fetched from Firebase.
    private ArrayList<Product> productlist;

    // DatabaseReference to Firebase to access product data within a specific store.
    private DatabaseReference db;

    // Adapter for the RecyclerView to display products.
    private ProductAdapter adapter;

    /**
     * Called when the activity is starting.
     * Initializes the activity, the RecyclerView and its adapter, and starts listening to Firebase for product data.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);

        // Retrieves the name of the store passed from the previous activity.
        store_name = getIntent().getStringExtra("name");

        // Sets the store name in the TextView.
        TextView store = findViewById(R.id.textView3);
        store.setText(store_name);

        // Initializes the RecyclerView and its layout manager.
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Initializes the list to store products.
        productlist = new ArrayList<>();

        adapter = new ProductAdapter(this, productlist, store_name);

        // Initializes the adapter with context, product list, and store name. Sets the adapter to the RecyclerView.
        rv.setAdapter(adapter);

        // Gets a reference to the "Products" node under the selected store in Firebase.
        db = FirebaseDatabase.getInstance().getReference("Stores").child(store_name).child("Products");

        // Adds a ValueEventListener to the database reference to listen for changes in the product data.
        db.addValueEventListener(new ValueEventListener() {

            // Clears the existing list to avoid duplicate entries.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productlist.clear();
                // Iterates over the snapshot's children, converting them to Product objects and adding them to the list.
                for(DataSnapshot x: snapshot.getChildren()) {
                    Product p = x.getValue(Product.class);
                    productlist.add(p);
                }
                // Notifies the adapter that the dataset has changed, causing the RecyclerView to refresh.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    /**
     * Navigates from S4 to S5 to create a new item in the existing shop.
     * @param view The view (button) that was clicked.
     */
    public void create_new_item(View view) {

        // Creates an Intent to start the S5 activity, passing along the store name.
        Intent i = new Intent(S4.this, S5.class);
        i.putExtra("name", store_name);
        startActivity(i);
        finish();// Finishes the current Activity, removing it from the stack.
    }


    /**
     * Cancels the current selection and navigates back to the store list.
     * @param view The view (button) that was clicked.
     */
    public void cancel(View view) {

        // Creates an Intent to start the S3 activity.
        Intent i = new Intent(S4.this, S3.class);
        startActivity(i);
        finish(); // Finishes the current Activity, removing it from the stack.
    }
}