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
import android.widget.SearchView;
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
import java.util.EventListener;
/**
 * Activity C5 for viewing and managing a user's shopping cart.
 * Allows users to see their products added to the cart, their total price, and proceed to buy.
 */
public class C5 extends AppCompatActivity {
  private RecyclerView recyclerView;
  private ArrayList<ProductInCart> ProductsInCartList;
  private DatabaseReference databaseReference;
  private ValueEventListener event;
  private ProductInCartAdapter adapter;
  private FirebaseDatabase database;
  private FirebaseUser user;
  private String userEmail;
  private String userEmailWithoutDot;
  private int totalCartPrice;

  private SearchView search_cart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c5);


        // Retrieve and display the store name from the previous activity
        Intent intent = getIntent();

        // setting the search bar for searching stores in list
        search_cart = findViewById(R.id.search_cart);
        search_cart.clearFocus();
        search_cart.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        String store_name = intent.getStringExtra("name");
        TextView textView = (TextView) findViewById(R.id.C5_Store_Name_textView);
        textView.setText(store_name);


        // update in real time for total cart price
        totalCartPrice = 0;


        // Initialize components and Firebase references
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail().toString();
        userEmailWithoutDot = userEmail.replace(".", "_");


        // handle the list of products in cart
        recyclerView = (RecyclerView)findViewById(R.id.C5_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductsInCartList = new ArrayList<>();
        adapter = new ProductInCartAdapter(this, ProductsInCartList);
        recyclerView.setAdapter(adapter);


        // save reference for products in the current user cart.
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products");


        // Event listener to update the cart UI in real-time
        event = new ValueEventListener() {
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

                totalCartPrice_textView.setText("Total cart Price : " + String.valueOf(totalCartPrice)+ " $");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReference.addValueEventListener(event);


        // Setup button actions
        button_buy();
        button_back();
    }

    /**
     * this function filters the recycle view element
     * @param text the filter text
     */
    private void filterList(String text) {
        ArrayList<ProductInCart> filteredList = new ArrayList<>();
        for(ProductInCart item : ProductsInCartList) {
            if(item.getName().trim().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.setFilteredList(filteredList);
    }


    /**
     * Handles the buy operation by moving the cart to an active order and then deleting the cart.
     */
    private void button_buy(){
        Button buy = (Button) findViewById(R.id.C5_Buy_button);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProductsInCartList.size() == 0){
                    Toast.makeText(C5.this, "You cannot buy 0 products...", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Process to copy the cart into the Active orders section and then delete the cart
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders").child("Carts").child(userEmailWithoutDot);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Copy cart details to Active orders
                            String CustomerName = snapshot.child("CustomerName").getValue(String.class);
                            String OrderID = snapshot.child("OrderID").getValue(String.class);
                            String StoreName = snapshot.child("StoreName").getValue(String.class);

                            database.getReference("Orders").child("Active").child(OrderID).child("CustomerName").setValue(CustomerName);
                            database.getReference("Orders").child("Active").child(OrderID).child("OrderID").setValue(OrderID);
                            database.getReference("Orders").child("Active").child(OrderID).child("StoreName").setValue(StoreName);
                            for (ProductInCart p : ProductsInCartList){
                                database.getReference("Orders").child("Active").child(OrderID).child("Products").child(p.getName()).child("Name").setValue(p.getName());
                                database.getReference("Orders").child("Active").child(OrderID).child("Products").child(p.getName()).child("Price").setValue(p.getPrice());
                                database.getReference("Orders").child("Active").child(OrderID).child("Products").child(p.getName()).child("Amount").setValue(p.getAmount());
                            }

                            // Delete the cart and navigate back to the store list
                            database.getReference("Orders").child("Carts").child(userEmailWithoutDot).removeValue();
                            databaseReference.removeEventListener(event);
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

    /**
     * Handles navigation back to the store list and removes the event listener.
     */
    private void button_back()
    {
        Button button = (Button) findViewById(R.id.C5_Back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeEventListener(event);
                finish();
            }
        });
    }
}
