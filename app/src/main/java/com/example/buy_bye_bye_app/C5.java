package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


        // buttons
        button_buy();
    }


    private void button_buy(){
        Button buy = (Button) findViewById(R.id.C5_Buy_button);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProductsInCartList.size() == 0){
                    Toast.makeText(C5.this, "You cannot buy 0 products...", Toast.LENGTH_SHORT).show();
                }
                else{
                    // get the cart values
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders").child("Carts").child(userEmailWithoutDot);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // get the values from the  Cart
                            String CustomerName = snapshot.child("CustomerName").getValue(String.class);
                            String OrderID = snapshot.child("OrderID").getValue(String.class);
                            String StoreName = snapshot.child("StoreName").getValue(String.class);

                            // copy them into the Active
                            database.getReference("Orders").child("Active").child(OrderID).child("CustomerName").setValue(CustomerName);
                            database.getReference("Orders").child("Active").child(OrderID).child("OrderID").setValue(OrderID);
                            database.getReference("Orders").child("Active").child(OrderID).child("StoreName").setValue(StoreName);
                            for (ProductInCart p : ProductsInCartList){
                                database.getReference("Orders").child("Active").child(OrderID).child("Products").child(p.getName()).child("Name").setValue(p.getName());
                                database.getReference("Orders").child("Active").child(OrderID).child("Products").child(p.getName()).child("Price").setValue(p.getPrice());
                                database.getReference("Orders").child("Active").child(OrderID).child("Products").child(p.getName()).child("Amount").setValue(p.getAmount());
                            }

                            // delete the cart from Carts.
                            database.getReference("Orders").child("Carts").child(userEmailWithoutDot).removeValue();

                            // back to the list of stores
                            startActivity(new Intent(C5.this, C3.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}