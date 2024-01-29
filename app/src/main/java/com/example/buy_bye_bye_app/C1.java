package com.example.buy_bye_bye_app;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class C1 extends AppCompatActivity {

    //et -> EditText
    //c-> client
    private EditText et_emailC;
    private EditText et_passwordC;
    private EditText et_addressC;
    private EditText et_visaC;
    private FirebaseAuth mAuth;

    private int email_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);

        mAuth = FirebaseAuth.getInstance();

    }

    public void sign_up(View view)
    {
        et_emailC =(EditText) findViewById(R.id.InputUsername);
        et_passwordC =findViewById(R.id.InputPassword);
        et_addressC =findViewById(R.id.InputAddress);
        et_visaC =findViewById(R.id.InputVisa);

        //TODO: check existence
        checkEmailExistence(et_emailC.getText().toString());
        while (email_check == 1)
        {
            checkEmailExistence(et_emailC.getText().toString());
        }

        mAuth.createUserWithEmailAndPassword(et_emailC.getText().toString(), et_passwordC.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Customer_user user1 = new Customer_user(et_emailC.getText().toString(), et_passwordC.getText().toString(), et_addressC.getText().toString(), et_visaC.getText().toString());
                        FirebaseDatabase.getInstance().getReference("user").child("customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1);
                        Log.d("FirebaseDebug" , "createUserWithEmail");
                        Intent i = new Intent(C1.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("FirebaseDebug", "createUserWithEmail:failure", task.getException());
                        showErrorToast("Authentication failed: " + task.getException().getMessage());
                    }
                });

    }


    public void checkEmailExistence(String emailToCheck) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child("customer");
        email_check = 0;
        userRef.orderByChild("email").equalTo(emailToCheck).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email already exists
                    showErrorToast("Email already exists. Please choose a different email.");
                    Log.d("FirebaseDebug", "Email already exists");
                    email_check = 1;
                } else {
                    // Email does not exist, you can proceed
                    Log.d("FirebaseDebug", "Email does not exist, adding new customer...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if any
                Log.e("FirebaseDebug", "Error checking email existence: " + databaseError.getMessage());
            }
        });
    }


    private void showErrorToast(String errorMessage) {
        // Display an error toast
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }










}