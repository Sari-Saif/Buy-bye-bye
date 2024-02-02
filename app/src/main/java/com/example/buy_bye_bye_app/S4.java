package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class S4 extends AppCompatActivity {

    private String store_name;
    private RecyclerView rv;
    private ArrayList<Product> productlist;
    private DatabaseReference db;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);


        store_name = getIntent().getStringExtra("name");

        TextView store = findViewById(R.id.textView3);
        store.setText(store_name);

        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        productlist = new ArrayList<>();

        adapter = new ProductAdapter(this, productlist, store_name);
        rv.setAdapter(adapter);

        db = FirebaseDatabase.getInstance().getReference("Stores").child(store_name).child("Products");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productlist.clear();
                for(DataSnapshot x: snapshot.getChildren()) {
                    Product p = x.getValue(Product.class);
                    productlist.add(p);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * this function moves from S4 to S5, in order to create new item inside existing shop
     * @param view the button
     */
    public void create_new_item(View view) {
        Intent i = new Intent(S4.this, S5.class);
        i.putExtra("name", store_name);
        startActivity(i);
    }
}