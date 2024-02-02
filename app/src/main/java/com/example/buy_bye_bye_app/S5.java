package com.example.buy_bye_bye_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class S5 extends AppCompatActivity {

    private final int GALLERY_REQUEST_CODE = 100;

    private String store_name;
    private ImageView img;
    private Button btn;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s5);

        store_name = getIntent().getStringExtra("name");

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Stores");

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

        String name = name_in.getText().toString();
        String price = price_in.getText().toString();
        String quantity = quantity_in.getText().toString();

        Product p = new Product("NULL", name, price, quantity);

        ref.child(store_name).child("Products").child(name).setValue(p);

        Intent i = new Intent(S5.this, S4.class);
        i.putExtra("name", store_name);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && data != null) {
            Uri uri =   data.getData();
            img.setImageURI(uri);
        }
    }
}