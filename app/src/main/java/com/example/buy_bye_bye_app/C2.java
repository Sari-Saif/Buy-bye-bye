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

public class C2 extends AppCompatActivity {

    private EditText et_emailC2;
    private EditText et_passwordC2;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<String> emailList = new ArrayList<>();
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c2);

        et_emailC2 = findViewById(R.id.InputEmail_Clogin);
        et_passwordC2 = findViewById(R.id.InputPassword_Clogin);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("buyers");

        childEventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
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

    public void sign_in(View view) {
        String email = et_emailC2.getText().toString().trim();
        String password = et_passwordC2.getText().toString().trim();

        // Check if email or password is empty
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(C2.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!emailList.contains(et_emailC2.getText().toString()))
        {
            Toast.makeText(C2.this, "Email not exist.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        String userEmail = mAuth.getCurrentUser().getEmail();
                        String[] parts = userEmail.split("@");
                        if (parts.length == 2) {
                            String username = parts[0];
                            Toast.makeText(C2.this, "Hello " + username + "!", Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(C2.this, C3.class);
                        startActivity(i);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(C2.this, "Your email or the password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}