package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class S4 extends AppCompatActivity {

    private String store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);

        store_name = getIntent().getStringExtra("name");

        TextView store = findViewById(R.id.textView3);
        store.setText(store_name);
    }

    /**
     * this function moves from S4 to S5, in order to create new item inside existing shop
     * @param view the button
     */
    public void create_new_item(View view) {
        Intent i = new Intent(S4.this, S5.class);
        i.putExtra("name", store_name);
        startActivity(i);
    }
}