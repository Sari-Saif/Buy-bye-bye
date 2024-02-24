package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This activity provides a UI for editing product details including the name, price, quantity, and image.
 * It uses Firebase Realtime Database for storing product information and Firebase Storage for product images.
 */
public class S4_pop extends AppCompatActivity {
    // Request code for gallery intent
    private final int GALLERY_REQUEST_CODE = 100;

    // Variables to hold product details passed from the previous activity
    private String old_name;
    private String old_price;
    private String old_quantity;
    private String old_url;
    private String store_name;

    // UI components for displaying and editing product details
    private EditText hold_price;
    private EditText hold_quantity;
    private TextView hold_name;
    private ImageView hold_img;

    // Button to trigger image change
    private Button change_img;
    // Bitmap for the new image
    private Bitmap bitmap;
    // Firebase Storage references
    private FirebaseStorage storage;
    private StorageReference storage_ref;
    private StorageReference storage_ref_2;


    // Firebase Database reference
    private DatabaseReference ref;
    private FirebaseDatabase db;


    // Variables for updated price and quantity
    private String new_price;
    private String new_quantity;
    // Reference to the product in the Firebase Database
    private DatabaseReference storeProductRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4_pop);

        // Initialize Firebase Storage and Database
        storage = FirebaseStorage.getInstance();
        storage_ref_2 = storage.getReference();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Stores");

        // Retrieve product details from intent
        old_name = getIntent().getStringExtra("name");
        old_price = getIntent().getStringExtra("price");
        old_quantity = getIntent().getStringExtra("quantity");
        store_name = getIntent().getStringExtra("store_name");

        // Set up UI components with the product details
        hold_name = findViewById(R.id.textView14);
        String header_name =  store_name + "/" + old_name;
        hold_name.setText(header_name);

        // Load the product image using Picasso
        hold_price = findViewById(R.id.editTextText6);
        hold_price.setHint(old_price);

        hold_quantity = findViewById(R.id.editTextText7);
        hold_quantity.setHint(old_quantity);

        // Load the product image using Picasso
        hold_img = findViewById(R.id.imageView4);
        Picasso.get().load(getIntent().getStringExtra("imageURL")).into(hold_img);
        // Set up the button to change the product image
        change_img = findViewById(R.id.button3);

        change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), GALLERY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of image selection.
        if(requestCode == GALLERY_REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            hold_img.setImageURI(uri);
        }
    }

    // Method to cancel and return to the previous screen.
    public void cancel(View view) {
        Intent i = new Intent(S4_pop.this, S3.class);
        startActivity(i);
    }

    // Method to navigate to the deletion confirmation screen.
    public void delete(View view) {
        Intent i = new Intent(S4_pop.this, S4_pop_pop.class);
        i.putExtra("store_name", store_name);
        i.putExtra("product_name", old_name);
        i.putExtra("image_url",getIntent().getStringExtra("imageURL"));
        startActivity(i);
    }

    // Method to apply changes to the product details.
    public void change(View view) {
        // Get new values or use old ones if no input was provided.
        new_price = hold_price.getText().toString();
        new_quantity = hold_quantity.getText().toString();

        if(new_price.isEmpty()) {
            new_price = old_price.replace("$", "");
        } if(new_quantity.isEmpty()) {
            new_quantity = old_quantity.replace(" pieces", "");
        }

        storeProductRef = FirebaseDatabase.getInstance().getReference("Stores")
                .child(store_name)
                .child("Products")
                .child(old_name)
                .child("price");
        storeProductRef.setValue(new_price);

        storeProductRef = FirebaseDatabase.getInstance().getReference("Stores")
                .child(store_name)
                .child("Products")
                .child(old_name)
                .child("Quantity");
        storeProductRef.setValue(new_quantity);


        bitmap = ((BitmapDrawable) hold_img.getDrawable()).getBitmap();

        String product_name = old_name.replaceAll("\\s", "");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        storage_ref = storage_ref_2.child(store_name).child(product_name + ".jpg");

        UploadTask uploadTask = storage_ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure of storage upload
                Log.e("pic", e.toString(), e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Storage upload success, now update the database
                storage_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Get the download URL
                        String imageUrl = downloadUri.toString();

                        storeProductRef = FirebaseDatabase.getInstance().getReference("Stores")
                                .child(store_name)
                                .child("Products")
                                .child(old_name)
                                .child("image");
                        storeProductRef.setValue(imageUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure of getting download URL
                        e.printStackTrace();
                    }
                });
            }
        });

        // Inform the user of successful update
        Toast.makeText(S4_pop.this, "Product updated successfully!", Toast.LENGTH_SHORT).show();

        // Redirect back to the S4 activity, passing the store name
        Intent i = new Intent(S4_pop.this, S4.class);
        i.putExtra("name", store_name);
        startActivity(i);
        finish();// Finish the current activity to prevent returning on back press
    }
}