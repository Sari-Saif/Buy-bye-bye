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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");


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
        //Intent i = new Intent(S8.this, S7.class);
        //i.putStringArrayListExtra("store_name_list", getIntent().getStringArrayListExtra("store_name_list"));
        //startActivity(i);
        finish();
    }

    public void accept(View view) {
        String orderID = getIntent().getStringExtra("order_id");

        databaseReference.child("Active").child(orderID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ActiveOrder order = snapshot.getValue(ActiveOrder.class);
                    String storename = order.getStoreName();

                    for(ActiveOrderItem item : list) {
                        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference()
                                .child("Stores")
                                .child(storename)
                                .child("Products")
                                .child(item.getName());
                        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String quantityStr  = snapshot.child("quantity").getValue(String.class);
                                    if (quantityStr  != null) {
                                        // Handle the quantity value

                                        int quantity = Integer.parseInt(quantityStr);
                                        int amount = Integer.parseInt(item.getAmount()); // Assuming you have item object
                                        int newQuantity = quantity - amount;

                                        if (newQuantity >= 0) {

                                            productsRef.child("quantity").setValue(String.valueOf(newQuantity))
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Log.d("Quantity", "Quantity updated successfully");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.e("Quantity", "Failed to update quantity", e);
                                                        }
                                                    });
                                        }

                                        Log.d("Quantity", "The quantity of " + item.getName() + " in " + storename + " is: " + quantity);
                                    } else {
                                        // Handle case where quantity is null
                                        Log.e("Quantity", "Quantity is null for " + item.getName() + " in " + storename);
                                    }
                                } else {
                                    // Handle case where product doesn't exist in the store
                                    Log.e("Quantity", item.getName() + " does not exist in " + storename);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database operation cancellation
                                Log.e("Quantity", "Database operation cancelled: " + error.getMessage());
                            }
                        });
                    }



                    // Move the order to the "History" section
                    databaseReference.child("History").child(orderID).setValue(snapshot.getValue())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // Order moved to history successfully, now remove it from the "Active" section
                                    databaseReference.child("Active").child(orderID).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
//                                                    Intent i = new Intent(S8.this, S6.class);
//                                                    startActivity(i);
                                                    finish();
                                                    // Order removed from "Active" successfully
                                                    // You may provide some feedback to the user here
                                                    Log.d("acceptOrder", "Order moved to history successfully");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Handle failure to remove the order from "Active"
                                                    Log.e("acceptOrder", "Error removing order from Active", e);
                                                    // You may provide feedback to the user here
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure to move the order to "History"
                                    Log.e("acceptOrder", "Error moving order to History", e);
                                    // You may provide feedback to the user here
                                }
                            });
                } else {
                    // Handle case where the order doesn't exist in the "Active" section
                    Log.e("acceptOrder", "Order not found in Active section");
                    // You may provide feedback to the user here
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database operation cancellation
                Log.e("acceptOrder", "Database operation cancelled: " + error.getMessage());
                // You may provide feedback to the user here
            }
        });
    }


}