package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * Activity C8 displays the detailed shopping list for a specific order,
 * including the customer name, store name, and total price of the order.
 */
public class C8 extends AppCompatActivity {
    private TextView header;// TextView for the header showing customer and store name
    private Intent intent;// Intent to receive data passed from the previous activity
    private RecyclerView rv;// RecyclerView to display the list of order items
    private ArrayList<ActiveOrderItem> list;// List to hold order items
    ActiveItemAdapter adapter; // Adapter for the RecyclerView
    DatabaseReference databaseReference;// Reference to the Firebase database
    private int total_price;// Variable to hold the total price of the order
    private TextView totPrice;// TextView to display the total price
    private SearchView search_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c8);

        // Retrieve data passed from the previous activity
        intent = getIntent();

        // setting the search bar for searching stores in list
        search_list = findViewById(R.id.search_list);
        search_list.clearFocus();
        search_list.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        String customer_name = intent.getStringExtra("customer_name");
        String store_name = intent.getStringExtra("store_name");
        String order_id = intent.getStringExtra("order_id");

        // Set the header text to include customer name and store name
        header = findViewById(R.id.textView16);
        String new_header_text = "The shopping list of: " + customer_name + ", at: " + store_name;
        header.setText(new_header_text);

        list = new ArrayList<>();
        adapter = new ActiveItemAdapter(this, list, store_name);

        // Initialize Firebase database reference to the "Orders" node
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        total_price = 0;
        totPrice = findViewById(R.id.Totalprice);// TextView for displaying total price

        // ValueEventListener to fetch and display order items and calculate total price
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();// Clear the list for new data

                for (DataSnapshot orderSnapshot : snapshot.child("History").getChildren()) {
                    String currentOrderId = orderSnapshot.child("OrderID").getValue(String.class);

                    // Match the current order with the selected order by ID
                    if (currentOrderId != null && currentOrderId.equals(order_id)) {
                        for (DataSnapshot productSnapshot : orderSnapshot.child("Products").getChildren()) {
                            ActiveOrderItem y = productSnapshot.getValue(ActiveOrderItem.class);
                            total_price += Integer.parseInt(y.getPrice());
                            list.add(y);// Add item to the list
                        }
                        // Update UI with the correct total_price here
                        String tPrice = "Total order price: " + total_price + "$";
                        totPrice.setText(tPrice);
                        break; // Exit the loop once products for the selected order are found
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter to refresh the view
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Setup RecyclerView with LinearLayoutManager and adapter
        rv = findViewById(R.id.history);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    /**
     * this function filters the recycle view element
     * @param text the filter text
     */
    private void filterList(String text) {
        ArrayList<ActiveOrderItem> filteredList = new ArrayList<>();
        for(ActiveOrderItem item : list) {
            if(item.getName().trim().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.setFilteredList(filteredList);
    }

    /**
     * Handles the back button click to navigate to the C6 activity and finish the current activity.
     */
    public void back(View view) {
        Intent i = new Intent(C8.this, C6.class);
        startActivity(i);
        finish();
    }
}