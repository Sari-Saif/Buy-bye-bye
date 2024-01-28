package com.example.buy_bye_bye_app;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ArrayList emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("customers");

        Reqister_Ok();

    }

    public void Reqister_Ok()
    {
        Button Register = (Button) findViewById(R.id.button);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_emailC =(EditText) findViewById(R.id.InputUsername);
                et_passwordC =findViewById(R.id.InputPassword);
                et_addressC =findViewById(R.id.InputAddress);
                et_visaC =findViewById(R.id.InputVisa);


                String email = et_emailC.getText().toString();
                String password = et_passwordC.getText().toString();
                String address = et_addressC.getText().toString();
                String visa = et_visaC.getText().toString();

                //TODO: ADD TO DATA BASE correctly!!
                check_name_id();

                boolean exist = email.contains(email);
                Log.d("FirebaseDebug", "EditText value: " + et_emailC.getText().toString());
                Log.d("FirebaseDebug", "exist? : " + exist);


                myRef.child(email).child("email").setValue(email);
                myRef.child(email).child("password").setValue(password);
                myRef.child(email).child("address").setValue(address);
                myRef.child(email).child("visa").setValue(visa);
                Intent i = new Intent(C1.this , MainActivity.class);
                startActivity(i);
            }
        });

    }


    private void check_name_id()
    {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                emails = new ArrayList<String>();

                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    String value = dataSnapshot.getValue(String.class);
                    emails.add(value);
                    Log.d("FirebaseDebug", "Value is: " + value);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }












}