package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class S8 extends AppCompatActivity {
    private TextView header;
    private Intent intent;

    private RecyclerView rv;

    private ArrayList<ActiveOrderItem> list;

    ActiveItemAdapter adapter;

    DatabaseReference databaseReference;


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
                            list.add(y);
                        }
                        break; // Exit the loop once products for the selected order are found
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rv = findViewById(R.id.recyclerView3);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }
}