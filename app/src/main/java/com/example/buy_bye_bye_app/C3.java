package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class C3 extends AppCompatActivity {


    RecyclerView rv;
    DatabaseReference db;
    StoreAdapter adapter;
    ArrayList<Store> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c3);
        move_to_C6_profile();
        retrive_store_list();
    }
    private void retrive_store_list() {
        rv = findViewById(R.id.List);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("Stores");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StoreAdapter(this, list);
        rv.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()) {
                    Store store = d.getValue(Store.class);
                    // if current.userID == store.userID :
                    list.add(store);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /*
   - move to C6 class
    */
    private void move_to_C6_profile() {
        ImageView to_s6 = (ImageView) findViewById(R.id.image_Button);
        to_s6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C3.this, C6.class));
            }
        });
    }
}