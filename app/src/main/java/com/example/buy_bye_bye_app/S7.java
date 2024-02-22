package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class S7 extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<ActiveOrder> list;
    DatabaseReference databaseReference;
    ActiveAdapter adapter;

    ArrayList<String> store_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);

        store_names = getIntent().getStringArrayListExtra("store_name_list");

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        list = new ArrayList<>();

        adapter = new ActiveAdapter(this, list);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot x : snapshot.child("Active").getChildren()) {
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

        rv = findViewById(R.id.recyclerView2);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }


    public void cancel(View view) {
        Intent i = new Intent(S7.this, S6.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
    }
}