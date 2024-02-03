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


public class S2 extends AppCompatActivity {

    /**
     * email input object
     */
    private EditText et_emailS2;

    /**
     * password input object
     */
    private EditText et_passwordS2;

    /**
     * Firebase REAL-TIME
     */
    private FirebaseDatabase database;

    /**
     * Firebase REAL-TIME reference
     */
    private DatabaseReference myRef;

    /**
     * Firebase AUTHENTICATION
     */
    private FirebaseAuth mAuth;

    /**
     * will hold all emails for checking if the entered email is already exists
     */
    private List<String> emaillist;

    /**
     * listener to handle child nodes in the rtdb
     */
    private ChildEventListener childEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);

        // setting UI input object's reference
        et_emailS2 = findViewById(R.id.InputEmail_s2);
        et_passwordS2 = findViewById(R.id.InputPassword_s2);

        // setting DB
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("sellers");

        // setting array list to save emails
        emaillist = new ArrayList<>();

        // adding listener to the email values
        childEventListener = new ChildEventListener() {

            // handling existing emails
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

        // listener for the sellers
        myRef.addChildEventListener(childEventListener);

    }

    /**
     * this function handles the login operation of the seller
     * @param view the login button
     */
    public void sign_in(View view) {

        // getting values of all entered information
        String email = et_emailS2.getText().toString().trim();
        String password = et_passwordS2.getText().toString().trim();

        // all fields are must, checking for empty
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(S2.this, "Please enter all the parameters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // checking if entered email is already exists. if true, abort signup.
        if(!emaillist.contains(email)) {
            Toast.makeText(S2.this, "Email does not exists!!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
           if(task.isSuccessful()) {
                String currEmail = mAuth.getCurrentUser().getEmail();
                String[] parts = currEmail.split("@");
                if(parts.length == 2) {
                    String currUsername = parts[0];
                    Toast.makeText(S2.this, "Hello " + currUsername + "!", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(S2.this, S3.class);
                startActivity(i);
           } else {
               Toast.makeText(S2.this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
           }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // removing listener to avoid memory leakage
        if (myRef != null && childEventListener != null) {
            myRef.removeEventListener(childEventListener);
        }
    }
}