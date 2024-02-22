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

public class S6 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView rv;
    private ArrayList<ActiveOrder> list;
    private DatabaseReference databaseReference;
    private ActiveAdapter adapter;

    ArrayList<String> store_names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s6);

        store_names = getIntent().getStringArrayListExtra("store_name_list");

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        list = new ArrayList<>();

        adapter = new ActiveAdapter(this, list);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot x : snapshot.child("History").getChildren()) {
                    ActiveOrder y = x.getValue(ActiveOrder.class);
                    if(store_names.contains(y.getStoreName())) {
                        list.add(y);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rv = findViewById(R.id.show_history);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public void cancel(View view) {
        Intent i = new Intent(S6.this, S3.class);
        startActivity(i);
    }

    public void edit_profile(View view) {
        Intent i = new Intent(S6.this, S9.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
        finish();
    }

    public void new_orders(View view) {
        Intent i = new Intent(S6.this, S7.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
        finish();
    }

    public void exit(View view) {
        mAuth.signOut();

        Intent i = new Intent(S6.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}