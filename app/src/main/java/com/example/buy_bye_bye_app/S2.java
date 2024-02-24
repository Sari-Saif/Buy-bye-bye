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
 * Activity class for handling seller login operations.
 */
public class S2 extends AppCompatActivity {

    // UI components for email and password input
    private EditText et_emailS2;
    private EditText et_passwordS2;

    // Firebase database instance for real-time operations
    private FirebaseDatabase database;

    // Reference to a specific node in the Firebase Realtime Database
    private DatabaseReference myRef;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    // List to store emails from the Firebase database to check existing accounts
    private List<String> emaillist;

    // Listener for database changes regarding seller information
    private ChildEventListener childEventListener;

    /**
     * Initializes the activity, UI components, and Firebase instances.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the most recent data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the view from the XML layout file
        setContentView(R.layout.activity_s2);

        // Initialize EditText components for email and password inputs
        et_emailS2 = findViewById(R.id.InputEmail_s2);
        et_passwordS2 = findViewById(R.id.InputPassword_s2);

        // Initialize Firebase Authentication and Database references
        mAuth = FirebaseAuth.getInstance();

        // Initialize the list to store existing emails
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("sellers");

        // Initialize the list to store existing emails
        emaillist = new ArrayList<>();

        // Setup a listener to track addition of child nodes in the Firebase database
        childEventListener = new ChildEventListener() {

            // Other override methods for handling database events, left empty as they are not used in this context
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email = snapshot.child("email").getValue().toString();
                emaillist.add(email);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        // Attach the listener to the database reference
        myRef.addChildEventListener(childEventListener);

    }

    /**
     * Handles the login operation when the login button is clicked.
     * @param view The view (button) that was clicked.
     */
    public void sign_in(View view) {

        // Retrieve and trim input from EditText fields
        String email = et_emailS2.getText().toString().trim();
        String password = et_passwordS2.getText().toString().trim();

        // Check if any field is empty and alert the user
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(S2.this, "Please enter all the parameters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the entered email exists in the list of registered emails
        if(!emaillist.contains(email)) {
            Toast.makeText(S2.this, "Email does not exists!!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Attempt to sign in with Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                // Greet the user by extracting username from email
                String currEmail = mAuth.getCurrentUser().getEmail();
                String[] parts = currEmail.split("@");
                if(parts.length == 2) {
                    String currUsername = parts[0];
                    Toast.makeText(S2.this, "Hello " + currUsername + "!", Toast.LENGTH_SHORT).show();
                }
                // Transition to another activity upon successful login
                Intent i = new Intent(S2.this, S3.class);
                startActivity(i);
                finish();
           } else {
               Toast.makeText(S2.this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
           }
        });
    }
    /**
     * Called by the Android system when the activity is about to be destroyed.
     * This method ensures the application cleans up resources to avoid memory leaks.
     * Specifically, it removes the ChildEventListener from the DatabaseReference
     * if both are not null. This is a crucial step in managing Firebase event listeners,
     * as failing to remove them can lead to memory leaks and unnecessary network traffic,
     * because the listener would continue to receive updates from the database even
     * after the activity is destroyed. It's a best practice to always remove any event
     * listeners in the onDestroy method of an activity to ensure proper resource management.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();// Call the superclass method to ensure proper destruction

        // Check if the DatabaseReference and ChildEventListener are not null
        if (myRef != null && childEventListener != null) {
            myRef.removeEventListener(childEventListener);// Remove the listener to prevent memory leaks
        }
    }
}