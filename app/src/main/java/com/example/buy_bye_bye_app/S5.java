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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class S5 extends AppCompatActivity {

    private final int GALLERY_REQUEST_CODE = 100;

    private String store_name;
    private ImageView img;
    private Button btn;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private Bitmap bitmap;
    private FirebaseStorage storage;
    private StorageReference storage_ref;
    private StorageReference storage_ref_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s5);

        store_name = getIntent().getStringExtra("name");

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

    public void create_new_item(View view) {
        EditText name_in = findViewById(R.id.editTextText);
        EditText price_in = findViewById(R.id.editTextText2);
        EditText quantity_in = findViewById(R.id.editTextText3);
        ImageView image_in = findViewById(R.id.imageView3);

        bitmap = ((BitmapDrawable) image_in.getDrawable()).getBitmap();

        String name = name_in.getText().toString();
        String price = price_in.getText().toString();
        String quantity = quantity_in.getText().toString();

        String product_name = name.replaceAll("\\s", "");

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            img.setImageURI(uri);
        }
    }
}