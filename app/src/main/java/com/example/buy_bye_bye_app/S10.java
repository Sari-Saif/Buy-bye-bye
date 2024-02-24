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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class S10 extends AppCompatActivity {
    private TextView header;
    private Intent intent;
    private RecyclerView rv;
    private ArrayList<ActiveOrderItem> list;
    ActiveItemAdapter adapter;
    DatabaseReference databaseReference;
    private int total_price;
    private TextView totPrice;

    private SearchView search_history_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s10);

        intent = getIntent();


        // setting the search bar for searching stores in list
        search_history_list = findViewById(R.id.search_history_list);
        search_history_list.clearFocus();
        search_history_list.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        header = findViewById(R.id.textView16);
        String new_header_text = "The shopping list of: " + customer_name + ", at: " + store_name;
        header.setText(new_header_text);

        list = new ArrayList<>();

        adapter = new ActiveItemAdapter(this, list, store_name);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        total_price = 0;

        totPrice = findViewById(R.id.textView17);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot orderSnapshot : snapshot.child("History").getChildren()) {
                    String currentOrderId = orderSnapshot.child("OrderID").getValue(String.class);

                    // Check if the current order matches the selected order_id
                    if (currentOrderId != null && currentOrderId.equals(order_id)) {
                        for (DataSnapshot productSnapshot : orderSnapshot.child("Products").getChildren()) {
                            ActiveOrderItem y = productSnapshot.getValue(ActiveOrderItem.class);
                            total_price += Integer.parseInt(y.getPrice());
                            list.add(y);
                        }
                        // Update UI with the correct total_price here
                        String tPrice = "Total order price: " + total_price + "$";
                        totPrice.setText(tPrice);
                        break; // Exit the loop once products for the selected order are found
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    public void cancel(View view) {
//        Intent i = new Intent(S10.this, S6.class);
//        startActivity(i);
        finish();
    }
}
