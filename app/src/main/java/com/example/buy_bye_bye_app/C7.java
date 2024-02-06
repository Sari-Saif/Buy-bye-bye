package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class C7 extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<ActiveOrder> list;
    DatabaseReference databaseReference;
    ActiveAdapter adapter;
    private FirebaseAuth mAuth;
    private String currentUserEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        list = new ArrayList<>();

        adapter = new ActiveAdapter(this, list);


        mAuth = FirebaseAuth.getInstance();
        // Get the current user's email
        if (mAuth.getCurrentUser() != null) {
            currentUserEmail = mAuth.getCurrentUser().getEmail();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot x : snapshot.child("Active").getChildren()) {

                    ActiveOrder order = x.getValue(ActiveOrder.class);
                    if (order != null && order.getOrderID() != null && !order.getOrderID().isEmpty() && order.getCustomerName() != null &&
                            order.getCustomerName().equals(currentUserEmail)) {
                        // Check if the order has any products before adding it to the list
                        list.add(order);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rv = findViewById(R.id.recyclerView2);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }


    public void cancel(View view) {
        Intent i = new Intent(C7.this, C6.class);
        startActivity(i);
    }
}