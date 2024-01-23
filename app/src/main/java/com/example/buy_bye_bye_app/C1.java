package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class C1 extends AppCompatActivity {

    //et -> EditText
    //c-> client
    private EditText et_emailC;
    private EditText et_passwordC;
    private EditText et_addressC;
    private EditText et_visaC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);

    }

    public void log_in(View view)
    {
        et_emailC =findViewById(R.id.InputUsername);
        et_passwordC =findViewById(R.id.InputPassword);
        et_addressC =findViewById(R.id.InputAddress);
        et_visaC =findViewById(R.id.InputVisa);

        //example
        String email = et_emailC.getText().toString();

        Button Register = (Button) findViewById(R.id.button);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: ADD TO DATA BASE !!
                Intent i = new Intent(C1.this , C3.class);
                startActivity(i);
            }
        });

    }











}