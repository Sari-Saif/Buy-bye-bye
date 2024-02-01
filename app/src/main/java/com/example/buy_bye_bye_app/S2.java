package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class S2 extends AppCompatActivity {

    private EditText et_emailS2;
    private EditText et_passwordS2;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);

        et_emailS2 = findViewById(R.id.InputEmail_s2);
        et_passwordS2 = findViewById(R.id.InputPassword_s2);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("seller");

    }

    public void sign_in(View view) {
        mAuth.signInWithEmailAndPassword(et_emailS2.getText().toString(), et_passwordS2.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        String email = et_emailS2.getText().toString();
                        String[] parts = email.split("@");
                        if (parts.length == 2) {
                            String username = parts[0];
                            Toast.makeText(S2.this, "Hello " + username + "!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(S2.this , S3.class);
                        startActivity(i);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(S2.this, "Your email or the password are incorrect.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }


}