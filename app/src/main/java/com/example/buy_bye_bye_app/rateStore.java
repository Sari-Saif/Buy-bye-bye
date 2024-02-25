package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rateStore extends AppCompatActivity {

    private Intent intent;
    private String StoreName;
    private TextView textViewHeader;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private int total_raters, sum_ratings;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_store);

        intent = getIntent();
        StoreName = intent.getStringExtra("store_name");

        textViewHeader = (TextView)findViewById(R.id.textViewHeader);
        textViewHeader.setText("Rate " + StoreName + " !");

        database = FirebaseDatabase.getInstance();

        ref = database.getReference().child("Stores").child(StoreName).child("total_raters");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total_raters = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ref = database.getReference().child("Stores").child(StoreName).child("sum_ratings");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sum_ratings = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void one_star(View view) {
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "total_raters").setValue(
                        String.valueOf(total_raters + 1));
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "sum_ratings").setValue(
                        String.valueOf(sum_ratings + 1));

        Intent i = new Intent(rateStore.this, C3.class);
        startActivity(i);
    }

    public void two_stars(View view) {
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "total_raters").setValue(
                String.valueOf(total_raters + 1));
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "sum_ratings").setValue(
                String.valueOf(sum_ratings + 2));

        Intent i = new Intent(rateStore.this, C3.class);
        startActivity(i);
    }

    public void three_stars(View view) {
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "total_raters").setValue(
                String.valueOf(total_raters + 1));
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "sum_ratings").setValue(
                String.valueOf(sum_ratings + 3));

        Intent i = new Intent(rateStore.this, C3.class);
        startActivity(i);
    }

    public void four_stars(View view) {
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "total_raters").setValue(
                String.valueOf(total_raters + 1));
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "sum_ratings").setValue(
                String.valueOf(sum_ratings + 4));

        Intent i = new Intent(rateStore.this, C3.class);
        startActivity(i);
    }

    public void five_stars(View view) {
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "total_raters").setValue(
                String.valueOf(total_raters + 1));
        FirebaseDatabase.getInstance().getReference().child("Stores").child(StoreName).child(
                "sum_ratings").setValue(
                String.valueOf(sum_ratings + 5));

        Intent i = new Intent(rateStore.this, C3.class);
        startActivity(i);
    }
}