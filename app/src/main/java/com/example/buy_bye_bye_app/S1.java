package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class S1 extends AppCompatActivity {

    //et -> EditText
    private EditText et_emailS1;
    private EditText et_passwordS1;
    private EditText et_addressS1;
    private EditText et_visaS1;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);

        et_emailS1 = findViewById(R.id.EmailInput_s1);
        et_passwordS1 = findViewById(R.id.PasswordInput_s1);
        et_addressS1 = findViewById(R.id.AddressInput_s1);
        et_visaS1 = findViewById(R.id.BankInput_s1);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("sellers");

    }
    public void sign_up(View view) {
        //TODO: Check if email already exist.
        mAuth.createUserWithEmailAndPassword(et_emailS1.getText().toString(), et_passwordS1.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Seller_user user1 = new Seller_user
                                (et_emailS1.getText().toString(), et_passwordS1.getText().toString(), et_addressS1.getText().toString(), et_visaS1.getText().toString());
                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1);
                        Toast.makeText(S1.this, "Thanks for register us!",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(S1.this , MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(S1.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }


}