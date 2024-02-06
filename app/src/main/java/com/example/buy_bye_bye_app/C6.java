package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class C6 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView rv;
    private ArrayList<ActiveOrder> list;
    private DatabaseReference databaseReference;
    private ActiveAdapter adapter;
    // Class field for currentUserEmail
    private String currentUserEmail = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c6);

        move_to_orders_pending_window();

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders/History");

        list = new ArrayList<>();

        adapter = new ActiveAdapter(this, list);

        // Get the current user's email
        if (mAuth.getCurrentUser() != null) {
            currentUserEmail = mAuth.getCurrentUser().getEmail();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    // Assuming ActiveOrder class has a field for StoreName and Products
                    ActiveOrder order = orderSnapshot.getValue(ActiveOrder.class);
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
                // Handle possible errors
            }
        });

        rv = findViewById(R.id.show_history_C);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    /*
      back button logic
     */
    public void back(View view) {
        Intent i = new Intent(C6.this, C3.class);
        startActivity(i);
    }
    /*
      order list button
    */
    private void move_to_orders_pending_window(){
        Button orders_pending = (Button) findViewById(R.id.C6_OrdersPending_button);
        orders_pending.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C6.this, C7.class));
            }
        });
    }

    /*
      edite profile logic button
    */
    public void edit_profile(View view) {
        Intent i = new Intent(C6.this, C9.class);
        startActivity(i);
    }
    /*
        exit button into main activity
     */
    public void exit(View view) {
        mAuth.signOut();

        Intent i = new Intent(C6.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}