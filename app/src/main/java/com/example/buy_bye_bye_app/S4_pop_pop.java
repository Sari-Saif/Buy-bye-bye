package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class S4_pop_pop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4_pop_pop);

        TextView tv = findViewById(R.id.textView16);
        tv.setText("Are you sure you want to delete parmenenty: " + getIntent().getStringExtra("product_name") + " ?");
    }

    public void cancel(View view) {
        Intent i = new Intent(S4_pop_pop.this, S3.class);
        startActivity(i);
    }

    public void delete(View view) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference sref = storage.getReferenceFromUrl(getIntent().getStringExtra("image_url"));
        sref.delete();

        DatabaseReference storeProductRef = FirebaseDatabase.getInstance().getReference("Stores")
                .child(getIntent().getStringExtra("store_name"))
                .child("Products")
                .child(getIntent().getStringExtra("product_name"));

        storeProductRef.removeValue();

        Toast.makeText(S4_pop_pop.this, "Product Deleted successfully!", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(S4_pop_pop.this, S3.class);
        startActivity(i);
    }
}