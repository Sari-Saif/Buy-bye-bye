package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
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


public class S3 extends AppCompatActivity {

    /**
     * this is the store's list UI element
     */
    private RecyclerView rv;

    /**
     * this is the ImageView UI element
     */
    private ImageView profile;

    /**
     * Firebase REAL-TIME reference
     */
    private DatabaseReference db;

    /**
     * adapter for getting the stores
     */
    private StoreAdapter adapter;

    /**
     * Firebase REAL-TIME
     */
    private FirebaseDatabase firebaseDatabase;

    /**
     * list for storring all the stores
     */
    private ArrayList<Store> list;

    /**
     * Firebase AUTHENTICATION
     */
    private FirebaseAuth mAuth;

    ArrayList<String> store_names;

    private SearchView search_stores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3);

        // setting the UI elements
        rv = findViewById(R.id.StoreList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        profile = findViewById(R.id.image_Button);

        // setting the search bar for searching stores in list
        search_stores = findViewById(R.id.search_stores);
        search_stores.clearFocus();
        search_stores.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        // setting the DB
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("Stores");
        mAuth = FirebaseAuth.getInstance();

        // setting the resources to retrieve the stores
        list = new ArrayList<>();
        adapter = new StoreAdapter(this, list);
        rv.setAdapter(adapter);

        // onCreate, we will call the retrieving of the store's
        retrieve_store_list();

        store_names = new ArrayList<>();
    }

    /**
     * this function filters the recycle view element
     * @param text the filter text
     */
    private void filterList(String text) {
        ArrayList<Store> filteredList = new ArrayList<>();
        for(Store item : list) {
            if(item.getStoreName().trim().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.setFilteredList(filteredList);
    }

    /**
     * this function handles the retrieving of the store's list of the current seller from the DB
     */
    private void retrieve_store_list() {

        // adding the listener for the stores in the db
        db.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    list.clear();
                    store_names.clear();

                    for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                        DataSnapshot ownerIDSnapshot = storeSnapshot.child("OwnerID");
                        String ownerID = ownerIDSnapshot.getValue(String.class);

                        // Example condition, adjust according to your actual requirement
                        if (ownerID.equals(currentUser.getUid())) {
                            // Assuming you have a Store class with appropriate fields
                            Store store = storeSnapshot.getValue(Store.class);
                            list.add(store);
                            store_names.add(store.getStoreName());
                        }
                    }
                    adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    /**
     * this function moves to current seller user's profile
     * @param view the `ImageView` element
     */
    public void move_to_S6_profile(View view) {
        Intent i = new Intent(S3.this, S6.class);
        i.putStringArrayListExtra("store_name_list", store_names);
        startActivity(i);
        finish();
    }

    /**
     * this function logging out the current seller user
     * @param view the button "Exit"
     */
    public void exit(View view) {
        mAuth.signOut();

        Intent i = new Intent(S3.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    /**
     * this function moves to new windows for creating new store
     * @param view the button "Add"
     */
    public void move_to_s3popup(View view) {
        startActivity(new Intent(S3.this, S3_Pop_up.class));
        finish();
    }
}