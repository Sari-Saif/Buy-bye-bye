package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;





public class S3 extends AppCompatActivity {

    Button add;

    RecyclerView rv;
    DatabaseReference db;
    StoreAdapter adapter;
    ArrayList<Store> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3);

        add = (Button) findViewById(R.id.Add_Button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), S3_Pop_up.class);
                startActivity(i);
            }
        });

        // S6
        move_to_S6_profile();
        
        retrive_store_list();

        on_click_shop();

    }


    private void retrive_store_list() {
        rv = findViewById(R.id.StoreList);
        db = FirebaseDatabase.getInstance().getReference("Stores");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StoreAdapter(this, list);
        rv.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()) {
                    Store store = d.getValue(Store.class);
                    // if current.userID == store.userID :
                    list.add(store);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    - "Add" Button  operation
     */
    private void move_to_S3_pop_window() {
        Button nextbutton = (Button) findViewById(R.id.Add_Button);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S3.this, S3_Pop_up.class));
            }
        });
    }
    /*
   - move to S6 class
    */
    private void move_to_S6_profile() {
        ImageView to_s6 = (ImageView) findViewById(R.id.image_Button);
        to_s6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S3.this, S6.class));
            }
        });
    }

    public void on_click_shop() {

        TextView to_s4 = (TextView)findViewById(R.id.tvStoreName);
        to_s4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S3.this, S4.class));
            }
        });
    }
}
