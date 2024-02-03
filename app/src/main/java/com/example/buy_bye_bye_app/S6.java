package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class S6 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s6);

        mAuth = FirebaseAuth.getInstance();
    }

    public void cancel(View view) {
        Intent i = new Intent(S6.this, S3.class);
        startActivity(i);
    }

    public void edit_profile(View view) {
        Intent i = new Intent(S6.this, S9.class);
        startActivity(i);
    }

    public void new_orders(View view) {
        Intent i = new Intent(S6.this, S7.class);
        startActivity(i);
    }

    public void exit(View view) {
        mAuth.signOut();

        Intent i = new Intent(S6.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}