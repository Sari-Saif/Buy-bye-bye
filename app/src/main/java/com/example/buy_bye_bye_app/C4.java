package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class C4 extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> ProductsList;
    DatabaseReference databaseReference;

    //ValueEventListener eventListener;

    ProductAdapter adapter;
    private TextView Store_Name;


    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c4);

        Intent intent = getIntent();
        String store_name = intent.getStringExtra("name");
        Toast.makeText(C4.this, "store: " + store_name, Toast.LENGTH_SHORT).show();

        Store_Name = findViewById(R.id.C4_Store_Name_textView);
        Store_Name.setText(store_name);

        recyclerView = (RecyclerView)findViewById(R.id.C4_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductsList = new ArrayList<>();

        adapter = new ProductAdapter(this, ProductsList, store_name);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Stores").child(store_name).child("Products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductsList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Product product = itemSnapshot.getValue(Product.class);
                    ProductsList.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        move_to_buyCart_window();
        button_back();

        // create Cart in Firebase
        create_cart();
    }

    private void move_to_buyCart_window(){
        Button nextbutton = (Button) findViewById(R.id.C4_Cart_button);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C4.this, C5.class));
            }
        });
    }

    private void create_cart()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail().toString();
        String userEmailWithoutDot = userEmail.replace(".", "_");

        database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("CustomerName").setValue(userEmail);
        database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("OrderID").setValue("IDIDIDIDIDID");
        database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("StoreName").setValue(Store_Name.getText().toString());
    }

    private void delete_cart()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail().toString();
        String userEmailWithoutDot = userEmail.replace(".", "_");

        database.getReference("Orders").child("Carts").child(userEmailWithoutDot).removeValue();
    }

    private void button_back(){
        Button back = (Button)findViewById(R.id.C4_Back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_cart();
            }
        });
    }
}