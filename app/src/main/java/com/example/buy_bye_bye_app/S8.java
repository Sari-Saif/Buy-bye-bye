package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class S8 extends AppCompatActivity {
    private TextView header;
    private TextView totPrice;
    private Intent intent;

    private RecyclerView rv;

    private ArrayList<ActiveOrderItem> list;

    ActiveItemAdapter adapter;

    DatabaseReference databaseReference;

    private int total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s8);

        intent = getIntent();

        String customer_name = intent.getStringExtra("customer_name");
        String store_name = intent.getStringExtra("store_name");
        String order_id = intent.getStringExtra("order_id");

        header = findViewById(R.id.textView14);
        String new_header_text = "The shopping list of: " + customer_name + ", at: " + store_name;
        header.setText(new_header_text);

        list = new ArrayList<>();

        adapter = new ActiveItemAdapter(this, list, store_name);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");


        total_price = 0;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot orderSnapshot : snapshot.child("Active").getChildren()) {
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

        String tPrice = "Total order price: " + total_price + "$";
        totPrice = findViewById(R.id.textView15);
        totPrice.setText(tPrice);

        rv = findViewById(R.id.recyclerView3);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }
    public void cancel(View view) {
        Intent i = new Intent(S8.this, S7.class);
        startActivity(i);
        finish();
    }

    public void accept(View view) {
        Log.e("TEST222", "$entered function$");

        String order_id = getIntent().getStringExtra("order_id").trim();

        // Get a reference to the "Orders" node
        DatabaseReference ordersReference = FirebaseDatabase.getInstance().getReference("Orders");

        // Get a reference to the current order in the "Active" node
        DatabaseReference activeOrderReference = ordersReference.child("Active").child(order_id);

        // Get the order data
        activeOrderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the order exists in "Active"
                if (snapshot.exists()) {
                    ActiveOrder order = snapshot.getValue(ActiveOrder.class);

                    // Get a reference to the "History" node
                    DatabaseReference historyReference = ordersReference.child("History").child(order_id);

                    // Move the order to "History"
                    historyReference.setValue(order)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Remove the order from "Active"
                                        activeOrderReference.removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Move to the next activity
                                                            Intent i = new Intent(S8.this, S6.class);
                                                            startActivity(i);
                                                            finish();
                                                        } else {
                                                            // Handle removal from "Active" failure
                                                            Log.e("ORDER_MOVE_ERROR", "Failed to remove order from Active");
                                                        }
                                                    }
                                                });
                                    } else {
                                        // Handle moving to "History" failure
                                        Log.e("ORDER_MOVE_ERROR", "Failed to move order to History");
                                    }
                                }
                            });
                } else {
                    // Handle case where order doesn't exist in "Active"
                    Log.e("ORDER_NOT_FOUND", "Order not found in Active");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                Log.e("DATABASE_ERROR", "Error reading data from Active");
            }
        });
    }

}