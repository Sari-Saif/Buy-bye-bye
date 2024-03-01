package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity class for handling the login process of buyers.
 */
public class C2 extends AppCompatActivity {

    // UI references for email and password input fields.
    private EditText et_emailC2;
    private EditText et_passwordC2;

    // Firebase database and authentication references.
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    // List to store emails to check for existence.
    private List<String> emailList = new ArrayList<>();
    // Listener to handle changes in the database's "buyers" node.
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c2);

        // Initializing UI components from layout.
        et_emailC2 = findViewById(R.id.InputEmail_Clogin);
        et_passwordC2 = findViewById(R.id.InputPassword_Clogin);

        // Getting instances of FirebaseAuth and FirebaseDatabase.
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("buyers");

        // Setting up a ChildEventListener to listen for updates to buyer emails.
        childEventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                // When a new child is added, its email is added to the emailList.
                String email = snapshot.child("email").getValue().toString();
                emailList.add(email);

                Log.d("UID", "New email added: " + email);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        // Add the ChildEventListener to the "buyers" node
        myRef.addChildEventListener(childEventListener);


    }

    /**
     * Handles the sign-in process when the sign-in button is pressed.
     *
     * @param view The view (button) that was clicked.
     */
    public void sign_in_costumer(View view) {
        // Retrieving input values from the UI components.
        String email = et_emailC2.getText().toString().trim();
        String password = et_passwordC2.getText().toString().trim();

        // Validating inputs: neither should be empty.
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(C2.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        // Checking if the email exists in the list before attempting to sign in.
        if(!emailList.contains(et_emailC2.getText().toString()))
        {
            Toast.makeText(C2.this, "Email not exist.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Attempting to sign in with the provided email and password.
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // On successful sign-in, greeting the user by their username extracted from the email.
                        String userEmail = mAuth.getCurrentUser().getEmail();
                        String[] parts = userEmail.split("@");
                        if (parts.length == 2) {
                            String username = parts[0];
                            Toast.makeText(C2.this, "Hello " + username + "!", Toast.LENGTH_SHORT).show();
                        }
                        // Redirecting to the next activity (C3) upon successful sign-in.
                        Intent i = new Intent(C2.this, C3.class);
                        startActivity(i);
                    } else {
                        // On failure, displaying a message to the user.
                        Toast.makeText(C2.this, "Your email or the password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void back_c2_main(View view)
    {
        finish();
    }
}