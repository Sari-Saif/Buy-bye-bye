package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Activity for editing the profile of the current user, allowing them to update their address and Visa card information.
 */
public class C9 extends AppCompatActivity {

    private EditText et_addressC9;// EditText field for the user's address
    private EditText et_visaC9;// EditText field for the user's Visa card information
    private TextView tv_usernameC9;// TextView for displaying the user's username
    private FirebaseDatabase database;// FirebaseDatabase instance
    private DatabaseReference myRef;// DatabaseReference object
    private FirebaseAuth mAuth;// FirebaseAuth instance


    private String email;// User's email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c9);

        // Initialize views
        tv_usernameC9 = findViewById(R.id.headerC1);
        et_addressC9 = findViewById(R.id.InputAddress_C9);
        et_visaC9 = findViewById(R.id.InputVisa_C9);

        // Initialize Firebase Auth and Database instances
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("buyers");

        // Retrieve and display the user's email from the database
        myRef.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    email = dataSnapshot.child("email").getValue(String.class);
                    if (email != null) {
                        // Log retrieved email and update UI
                        Log.d("Email", "Email for UID " + mAuth.getUid() + ": " + email);

                        // Trigger UI updates here or call a method to handle UI updates
                        updateUI();
                    } else {
                        Log.d("Email", "Email not found for UID " + mAuth.getUid());
                    }
                } else {
                    Log.d("Email", "No data found for UID " + mAuth.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Email", "Error retrieving email: " + databaseError.getMessage());
            }
        });
    }

    /**
     * Updates the UI with the user's username extracted from their email.
     */
    private void updateUI() {
        if (email != null) {
            String[] parts = email.split("@");
            String change = "edit " + parts[0] +"'s profile";
            tv_usernameC9.setText(change);
        }
    }

    /**
     * Handles the update action to change address and/or Visa information.
     */
    public void Update(View view)
    {
        String address = et_addressC9.getText().toString().trim();
        String visa = et_visaC9.getText().toString().trim();

        // Ensure at least one field is filled
        if (TextUtils.isEmpty(address) && TextUtils.isEmpty(visa) ) {
            Toast.makeText(C9.this, "Please enter at least one parameter!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update address if provided
        if (!TextUtils.isEmpty(address))
        {
            myRef.child(mAuth.getUid()).child("address").setValue(address);
        }

        // Update Visa information if provided
        if (!TextUtils.isEmpty(visa))
        {
            myRef.child(mAuth.getUid()).child("visa").setValue(visa);
        }

        Toast.makeText(C9.this, "Changed!", Toast.LENGTH_SHORT).show();



    }

    /**
     * Cancels the edit operation and returns to the previous activity.
     */
    public void Cancel(View view) {
        Intent i = new Intent(C9.this , C6.class);// Navigate back to the C6 activity
        startActivity(i);
    }
}