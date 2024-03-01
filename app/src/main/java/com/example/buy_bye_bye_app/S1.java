package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * An Android Activity class for user registration, specifically designed for seller accounts.
 * It demonstrates the integration of Firebase Authentication and Realtime Database for managing user data.
 */
public class S1 extends AppCompatActivity {

    // EditText fields for user inputs: email, password, address, and bank information.
    private EditText et_emailS;// email input object
    private EditText et_passwordS;// password input object
    private EditText et_addressS;// address input object
    private EditText et_Bank;// bank input object

    // Firebase components for authentication and database operations.
    private FirebaseAuth database;// Firebase AUTHENTICATION
    private FirebaseDatabase db;// Firebase REAL-TIME
    private DatabaseReference ref;// Firebase REAL-TIME reference

    // A list to store emails from the database to prevent duplicate registrations.
    private List<String> emaillist;// listener to handle child nodes in the RTDB

    private ChildEventListener childEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);// Setting the layout for the activity

        // Initializing EditText fields with their respective IDs from the layout.
        et_emailS = findViewById(R.id.InputUsername);
        et_passwordS = findViewById(R.id.InputPassword);
        et_addressS = findViewById(R.id.InputAddress);
        et_Bank = findViewById(R.id.InputBank);

        // Initializing Firebase database and authentication instances.
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("users").child("sellers");
        database = FirebaseAuth.getInstance();

        // Initializing the email list to track existing emails.
        emaillist = new ArrayList<>();

        // Setting up a ChildEventListener to populate the email list from the database.
        childEventListener = new ChildEventListener() {

            // handling existing emails
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email = snapshot.child("email").getValue().toString();// Fetching email from snapshot
                emaillist.add(email);// Adding email to the list
            }
            // Other ChildEventListener methods are not utilized but are required to be overridden.
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        // Attaching the ChildEventListener to the database reference.
        ref.addChildEventListener(childEventListener);
    }

    /**
     * Handles the seller sign-up process, including input validation and Firebase integration for authentication and data storage.
     * @param view The view (button) that triggers this method upon click.
     */
    public void sign_up_seller(View view) {
        // Retrieving and trimming user input from EditText fields.
        String email = et_emailS.getText().toString().trim();
        String password = et_passwordS.getText().toString().trim();
        String address = et_addressS.getText().toString().trim();
        String bank = et_Bank.getText().toString().trim();

        // Checking for empty fields and providing feedback.
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(address) || TextUtils.isEmpty(bank) ) {
            Toast.makeText(S1.this, "Please enter all the parameters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Checking for duplicate email and providing feedback.
        if(emaillist.contains(email)) {
            Toast.makeText(S1.this, "Email already exist!!", Toast.LENGTH_SHORT).show();
            return;
        }

        // If all validations pass, proceed with Firebase Authentication to create a new user.
        database.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                // Creating a Seller object and saving it to the Realtime Database under the user's UID.
                Seller s = new Seller(email, password, address, bank);
                ref.child(database.getCurrentUser().getUid()).setValue(s);

                // Shows a success message and navigates to the MainActivity
                Toast.makeText(S1.this, "New Seller Registered!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(S1.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                // Shows an error message if the registration fails
                Toast.makeText(S1.this, "Please enter valid information!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // removing listener to avoid memory leakage
        if (ref != null && childEventListener != null) {
            ref.removeEventListener(childEventListener);
        }
    }

    public void back_s1_main(View view) {
        finish();
    }
}