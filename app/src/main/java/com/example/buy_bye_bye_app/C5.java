package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class C5 extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProductInCart> ProductsInCartList;
    DatabaseReference databaseReference;

    ProductInCartAdapter adapter;

    FirebaseDatabase database;
    FirebaseUser user;
    String userEmail;
    String userEmailWithoutDot;

    int totalCartPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c5);

        totalCartPrice = 0;

        // save the userEmail
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail().toString();
        userEmailWithoutDot = userEmail.replace(".", "_");

        recyclerView = (RecyclerView)findViewById(R.id.C5_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductsInCartList = new ArrayList<>();

        adapter = new ProductInCartAdapter(this, ProductsInCartList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductsInCartList.clear();
                totalCartPrice = 0;
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ProductInCart product_in_cart = itemSnapshot.getValue(ProductInCart.class);
                    ProductsInCartList.add(product_in_cart);

                    totalCartPrice += product_in_cart.getTotalPrice();
                }
                TextView totalCartPrice_textView = (TextView)findViewById(R.id.C5_TotalPriceOfCart);
                totalCartPrice_textView.setText(String.valueOf(totalCartPrice));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}