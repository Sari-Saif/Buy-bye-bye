package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class C1 extends AppCompatActivity {

    //et -> EditText
    //c-> client
    private EditText et_emailC;
    private EditText et_passwordC;
    private EditText et_addressC;
    private EditText et_visaC;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Seller");

        Reqister_Ok();

    }

    public void Reqister_Ok()
    {
        et_emailC =findViewById(R.id.InputUsername);
        et_passwordC =findViewById(R.id.InputPassword);
        et_addressC =findViewById(R.id.InputAddress);
        et_visaC =findViewById(R.id.InputVisa);

        //example
        String email = et_emailC.getText().toString();
        String password = et_passwordC.getText().toString();
        String address = et_addressC.getText().toString();
        String visa = et_visaC.getText().toString();


        Button Register = (Button) findViewById(R.id.button);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: ADD TO DATA BASE !!
                FirebaseDatabase.getInstance().getReference("seller").child(email).child("email").setValue(email);
                FirebaseDatabase.getInstance().getReference("seller").child(email).child("password").setValue(password);
                FirebaseDatabase.getInstance().getReference("seller").child(email).child("address").setValue(address);
                FirebaseDatabase.getInstance().getReference("seller").child(email).child("visa").setValue(visa);
                Intent i = new Intent(C1.this , MainActivity.class);
                startActivity(i);
            }
        });

    }











}