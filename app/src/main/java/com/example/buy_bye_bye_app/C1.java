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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class C1 extends AppCompatActivity {

    private EditText et_emailC;
    private EditText et_passwordC;
    private EditText et_addressC;
    private EditText et_visaC;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<String> emailList = new ArrayList<>();
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);

        et_emailC = findViewById(R.id.InputEmail);
        et_passwordC = findViewById(R.id.InputPassword_Clogin);
        et_addressC = findViewById(R.id.InputAddress);
        et_visaC = findViewById(R.id.InputVisa);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("buyers");

        // Initialize ChildEventListener
        childEventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email = snapshot.child("email").getValue().toString();
                emailList.add(email);

                Log.d("UID", "New email added: " + email);
                // Now uidList contains all the UIDs
                // You can use uidList as needed
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

    public void sign_up_costumer(View view) {

        String email = et_emailC.getText().toString().trim();
        String password = et_passwordC.getText().toString().trim();
        String address = et_addressC.getText().toString().trim();
        String visa = et_visaC.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(address) || TextUtils.isEmpty(visa) ) {
            Toast.makeText(C1.this, "Please enter all the parameters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(emailList.contains(et_emailC.getText().toString()))
        {
            Toast.makeText(C1.this, "Email already exist.", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Check if email already exist.
        mAuth.createUserWithEmailAndPassword(et_emailC.getText().toString(), et_passwordC.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Customer_user user1 = new Customer_user
                                (et_emailC.getText().toString(), et_passwordC.getText().toString(), et_addressC.getText().toString(), et_visaC.getText().toString());
                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1);
                        Toast.makeText(C1.this, "Thanks for register us!",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(C1.this , MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(C1.this, "please enter valid email and password",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove the ChildEventListener to avoid memory leaks
        if (myRef != null && childEventListener != null) {
            myRef.removeEventListener(childEventListener);
        }
    }

    public void back_c1_main(View view)
    {
        finish();
    }



}
