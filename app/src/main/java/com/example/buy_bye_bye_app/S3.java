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

        mAuth = FirebaseAuth.getInstance();
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
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    list.clear();

                    for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                        DataSnapshot ownerIDSnapshot = storeSnapshot.child("OwnerID");
                        String ownerID = ownerIDSnapshot.getValue(String.class);
                        Log.d("sari", "ownerID: " + ownerID);
                        Log.d("sari", "store ownerID: " + ownerID);

                        // Example condition, adjust according to your actual requirement
                        if (ownerID.equals(currentUser.getUid())) {
                            // Assuming you have a Store class with appropriate fields
                            Store store = storeSnapshot.getValue(Store.class);
                            list.add(store);
                        }
                    }

                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("sari", "Database error: " + error.getMessage());
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

    public void exit(View view) {
        mAuth.signOut();

        Intent i = new Intent(S3.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    public void move_to_s3popup(View view) {
        startActivity(new Intent(S3.this, S3_Pop_up.class));
    }
}