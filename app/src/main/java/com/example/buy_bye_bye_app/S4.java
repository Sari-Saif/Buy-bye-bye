package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class S4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);
    }

    /**
     * this function moves from S4 to S5, in order to create new item inside existing shop
     * @param view the button
     */
    public void create_new_item(View view) {
        Intent i = new Intent(S4.this, S5.class);
        startActivity(i);
    }
}