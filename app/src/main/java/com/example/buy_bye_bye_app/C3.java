package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class C3 extends AppCompatActivity {


    RecyclerView rv;
    DatabaseReference db;
    StoreAdapter adapter;
    ArrayList<Store> list;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c3);


        mAuth = FirebaseAuth.getInstance();

        //move_to_C4_profile();
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
                list.clear();
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
        ImageView to_c6 = (ImageView) findViewById(R.id.image_Button);
        to_c6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C3.this, C6.class));
            }
        });
    }
    /*
- move to C4 class
*/


    /*
      exit button into main activity
   */
    public void ExitToMainActivity(View view) {
        mAuth.signOut();

        Intent i = new Intent(C3.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


//    private void move_to_C4_profile() {
//        Button to_c4 = (Button) findViewById(R.id.C4_Button);
//        to_c4.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(C3.this, C4.class));
//            }
//        });
//    }
}