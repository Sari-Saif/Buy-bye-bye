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
/**
 * This activity handles the confirmation and deletion of a product.
 * It prompts the user to confirm the deletion and performs the deletion of product data from Firebase.
 */
public class S4_pop_pop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4_pop_pop);

        // Retrieve and display the product name to confirm deletion
        TextView tv = findViewById(R.id.textView16);
        tv.setText("Are you sure you want to delete parmenenty: " + getIntent().getStringExtra("product_name") + " ?");
    }

    /**
     * Cancels the deletion and navigates back to the previous screen.
     * @param view The view that triggered this method (Cancel button).
     */
    public void cancel(View view) {
        // Navigate back to S3 activity
        Intent i = new Intent(S4_pop_pop.this, S3.class);
        startActivity(i);
    }
    /**
     * Deletes the product from Firebase storage and database, then navigates back to the previous screen.
     * @param view The view that triggered this method (Delete button).
     */
    public void delete(View view) {
        // Delete the product image from Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference sref = storage.getReferenceFromUrl(getIntent().getStringExtra("image_url"));
        sref.delete();

        // Delete the product details from Firebase Realtime Database
        DatabaseReference storeProductRef = FirebaseDatabase.getInstance().getReference("Stores")
                .child(getIntent().getStringExtra("store_name"))
                .child("Products")
                .child(getIntent().getStringExtra("product_name"));

        storeProductRef.removeValue();

        Toast.makeText(S4_pop_pop.this, "Product Deleted successfully!", Toast.LENGTH_SHORT).show();
        // Navigate back to S3 activity
        Intent i = new Intent(S4_pop_pop.this, S3.class);
        startActivity(i);
    }
}