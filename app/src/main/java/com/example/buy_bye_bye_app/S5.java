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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * S5 activity allows users to add a new product including its image, name, price, and quantity to Firebase.
 * It includes functionality to upload an image from the device's gallery to Firebase Storage
 * and save product details in Firebase Realtime Database under a specific store.
 */
public class S5 extends AppCompatActivity {

    private final int GALLERY_REQUEST_CODE = 100;

    private String store_name;// Store name passed from the previous activity
    private ImageView img;// ImageView to display the selected image
    private Button btn;// Button to trigger the image selection
    private FirebaseDatabase db; // Firebase Database instance
    private DatabaseReference ref;// Reference to the database path
    private Bitmap bitmap;// Bitmap to store the selected image
    private FirebaseStorage storage;// Firebase Storage instance
    private StorageReference storage_ref;// Reference to the storage path
    private StorageReference storage_ref_2; // Another reference to the storage path, used for uploads

    private TextView tv; // TextView to display the store name

    private ArrayList<String> productNames;

    private boolean exit_func = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s5);

        store_name = getIntent().getStringExtra("name");

        tv = findViewById(R.id.textView2);
        tv.setText(store_name);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Stores");

        storage = FirebaseStorage.getInstance();
        storage_ref_2 = storage.getReference();


        img = findViewById(R.id.imageView3);
        btn = findViewById(R.id.button7);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), GALLERY_REQUEST_CODE);
            }
        });
    }
    /**
     * Creates a new item with details entered by the user and uploads the item's image to Firebase Storage.
     */
    public void create_new_item(View view) {
        Log.e("err_products", "enter c_n_i");

        // Retrieve item details from EditText fields
        EditText name_in = findViewById(R.id.editTextText);
        EditText price_in = findViewById(R.id.editTextText2);
        EditText quantity_in = findViewById(R.id.editTextText3);
        ImageView image_in = findViewById(R.id.imageView3);

        // Get the text inputs as strings
        String name = name_in.getText().toString().trim();
        String price = price_in.getText().toString().trim();
        String quantity = quantity_in.getText().toString().trim();

        // Format the product name to remove spaces (for use in the storage path)
        String product_name = name.replaceAll("\\s", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Stores").child(store_name).child("Products");

        // Listener to retrieve data from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productNames = new ArrayList<>();

                // Iterate through all children under "Stores"
                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    // Assuming the store name is stored as a child node with key "name"
                    String productName = storeSnapshot.child("name").getValue(String.class);
                    if (productName != null) {
                        productNames.add(productName);
                    }
                }

                Log.e("err_products", "product list: " + productNames.toString());

                // Check if the entered store name is already taken
                boolean productNameExists = false;
                for (String existingProduct : productNames) {
                    if (existingProduct.equals(name)) {
                        productNameExists = true;
                        break;
                    }
                }

                // If the store name already exists, show a toast and return
                if (productNameExists) {
                    Toast.makeText(S5.this, "Sorry, but this product name is already taken!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(quantity) || image_in.getDrawable() == null) {
                    Toast.makeText(S5.this, "Sorry, but params cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert the selected image to a Bitmap
                bitmap = ((BitmapDrawable) image_in.getDrawable()).getBitmap();

                // Compress the bitmap to a JPEG and convert to a byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                // Define the storage path in Firebase Storage and upload the image
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

                                // Create a Product object
                                Product p = new Product(imageUrl, name, price, quantity);

                                // Update the database
                                ref.child(store_name).child("Products").child(name).setValue(p);

                                Toast.makeText(S5.this, "Created " + name + "!",
                                        Toast.LENGTH_SHORT).show();

                                // Navigate to the desired activity
                                Intent i = new Intent(S5.this, S4.class);
                                i.putExtra("name", store_name);
                                startActivity(i);
                                finish();
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("err_product_names", "Failed to read store names.", databaseError.toException());
            }
        });
    }

    /**
     * Handles the result from the gallery picker, setting the chosen image on the ImageView.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            img.setImageURI(uri);
        }
    }

    public void cancel(View view) {
        Intent i = new Intent(S5.this, S4.class);
        i.putExtra("name", store_name);
        startActivity(i);
        finish();
    }
}