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

public class C1 extends AppCompatActivity {

    private EditText et_emailC;
    private EditText et_passwordC;
    private EditText et_addressC;
    private EditText et_visaC;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

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
        myRef = database.getReference("users");

    }

    public void sign_up(View view) {
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
                        Toast.makeText(C1.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
