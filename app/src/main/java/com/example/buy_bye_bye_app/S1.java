package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class S1 extends AppCompatActivity {

    //et -> EditText
    //c-> client
    private EditText et_emailS;
    private EditText et_passwordS;
    private EditText et_addressS;
    private EditText et_Bank;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Reqister_Ok();

    }

    public void Reqister_Ok()
    {
        Button Register = (Button) findViewById(R.id.button);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_emailS =(EditText) findViewById(R.id.InputUsername);
                et_passwordS =findViewById(R.id.InputPassword);
                et_addressS =findViewById(R.id.InputAddress);
                et_Bank =findViewById(R.id.InputBank);


                String email = et_emailS.getText().toString();
                String password = et_passwordS.getText().toString();
                String address = et_addressS.getText().toString();
                String visa = et_Bank.getText().toString();

                //TODO: ADD TO DATA BASE !!
                Log.d("FirebaseDebug", "EditText value: " + et_emailS.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("Email").setValue(email);
                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("password").setValue(password);
                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("Address").setValue(address);
                FirebaseDatabase.getInstance().getReference().child("user").child("seller").child(email).child("Bank").setValue(visa);
                Intent i = new Intent(S1.this , MainActivity.class);
                startActivity(i);
            }
        });

    }
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

        mAuth.createUserWithEmailAndPassword(et_emailS.getText().toString(), et_passwordS.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Customer_user user1 = new Customer_user(et_emailS.getText().toString(), et_passwordS.getText().toString(), et_addressS.getText().toString(), et_Bank.getText().toString());
                        FirebaseDatabase.getInstance().getReference("user").child("customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1);
                        Intent i = new Intent(S1.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("FirebaseDebug", "createUserWithEmail:failure", task.getException());
                        showErrorToast("Authentication failed: " + task.getException().getMessage());
                    }
                });

    }




}