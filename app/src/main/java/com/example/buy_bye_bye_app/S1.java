package com.example.buy_bye_bye_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class S1 extends AppCompatActivity {
    //et -> EditText
    //c-> client
    private EditText et_emailS;
    private EditText et_passwordS;
    private EditText et_addressS;
    private EditText et_Bank;
    private FirebaseAuth database;
    private int email_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);

        database = FirebaseAuth.getInstance();

    }

//    public void Reqister_Ok()
//    {
//        Button Register = (Button) findViewById(R.id.button);
//
//        Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                et_emailS =(EditText) findViewById(R.id.InputUsername);
//                et_passwordS =findViewById(R.id.InputPassword);
//                et_addressS =findViewById(R.id.InputAddress);
//                et_Bank =findViewById(R.id.InputBank);
//
//
//                String email = et_emailS.getText().toString();
//                String password = et_passwordS.getText().toString();
//                String address = et_addressS.getText().toString();
//                String visa = et_Bank.getText().toString();
//
//                //TODO: ADD TO DATA BASE !!
//                Log.d("FirebaseDebug", "EditText value: " + et_emailS.getText().toString());
//                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("Email").setValue(email);
//                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("password").setValue(password);
//                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("Address").setValue(address);
//                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("Bank").setValue(visa);
//                Intent i = new Intent(S1.this , MainActivity.class);
//                startActivity(i);
//            }
//        });
//
//    }
    public void sign_up(View view)
    {
        et_emailS =(EditText) findViewById(R.id.InputUsername);
        et_passwordS =findViewById(R.id.InputPassword);
        et_addressS =findViewById(R.id.InputAddress);
        et_Bank =findViewById(R.id.InputVisa);

        //TODO: check existence
        checkEmailExistence(et_emailS.getText().toString());
        if (email_check == 1)
        {
            return;
        }

        database.createUserWithEmailAndPassword(et_emailS.getText().toString(), et_passwordS.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Customer_user user1 = new Customer_user(et_emailS.getText().toString(), et_passwordS.getText().toString(), et_addressS.getText().toString(), et_Bank.getText().toString());
                        FirebaseDatabase.getInstance().getReference("user").child("customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1);
                        Intent i = new Intent(S1.this, S3.class);
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