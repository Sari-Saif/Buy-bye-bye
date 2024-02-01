package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Owner;
import java.util.ArrayList;



public class S3 extends AppCompatActivity {

    RecyclerView rv;
    DatabaseReference db;
    StoreAdapter adapter;
    ArrayList<Store> list;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3);

       // move_to_C4_profile();
        move_to_S6_profile();
        retrive_store_list();
    }
    private void retrive_store_list() {
        rv = findViewById(R.id.StoreList);
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

                FirebaseUser currentUser = mAuth.getCurrentUser();                list.clear();
                list.clear(); // Assuming 'list' is previously declared and initialized

                if (currentUser == null) {
                    Log.d("sari", "Current user is null");
                    return; // Exit if no user is logged in
                }

                String currentUserEmail = currentUser.getEmail(); // Can be null if the user does not have an email
                for (DataSnapshot d : snapshot.getChildren()) {
                    Store store = d.getValue(Store.class); // Assuming 'Store' is correctly mapped to your Firebase structure

                    // Example condition, adjust according to your actual requirement
                    String ownerID = d.child("OwnerID").getValue(String.class);
                    if (currentUserEmail != null && ownerID != null && currentUserEmail.equals(ownerID)) {
                        list.add(store);
                    }
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
    private void move_to_S6_profile() {
        ImageView to_c6 = (ImageView) findViewById(R.id.image_Button);
        to_c6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S3.this, S6.class));
            }
        });
    }

}