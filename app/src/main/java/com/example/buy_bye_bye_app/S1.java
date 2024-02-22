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


public class S1 extends AppCompatActivity {

    /**
     * email input object
     */
    private EditText et_emailS;

    /**
     * password input object
     */
    private EditText et_passwordS;

    /**
     * address input object
     */
    private EditText et_addressS;

    /**
     * band input object
     */
    private EditText et_Bank;

    /**
     * Firebase AUTHENTICATION
     */
    private FirebaseAuth database;

    /**
     * Firebase REAL-TIME
     */
    private FirebaseDatabase db;

    /**
     * Firebase REAL-TIME reference
     */
    private DatabaseReference ref;

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
        setContentView(R.layout.activity_s1);

        // setting UI input object's reference
        et_emailS = findViewById(R.id.InputUsername);
        et_passwordS = findViewById(R.id.InputPassword);
        et_addressS = findViewById(R.id.InputAddress);
        et_Bank = findViewById(R.id.InputBank);

        // setting DB
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("users").child("sellers");
        database = FirebaseAuth.getInstance();

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
        ref.addChildEventListener(childEventListener);
    }

    /**
     * this function handles the sign up of the seller (realtime + authentication)
     * @param view the signup button in S1 UI
     */
    public void sign_up(View view) {

        // getting values of all entered information
        String email = et_emailS.getText().toString().trim();
        String password = et_passwordS.getText().toString().trim();
        String address = et_addressS.getText().toString().trim();
        String bank = et_Bank.getText().toString().trim();

        // all fields are must, checking for empty
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(address) || TextUtils.isEmpty(bank) ) {
            Toast.makeText(S1.this, "Please enter all the parameters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // checking if entered email is already exists. if true, abort signup.
        if(emaillist.contains(email)) {
            Toast.makeText(S1.this, "Email already exist!!", Toast.LENGTH_SHORT).show();
            return;
        }

        // else, all is fine, add the user to the AUTH + RTDB
        database.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Seller s = new Seller(email, password, address, bank);
                ref.child(database.getCurrentUser().getUid()).setValue(s);

                Toast.makeText(S1.this, "New Seller Registered!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(S1.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
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
}