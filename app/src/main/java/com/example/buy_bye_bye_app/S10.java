package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class S10 extends AppCompatActivity {
    private TextView header;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s10);

        intent = getIntent();

        String customer_name = intent.getStringExtra("customer_name");
        String store_name = intent.getStringExtra("store_name");
        String order_id = intent.getStringExtra("order_id");

        header = findViewById(R.id.textView16);
        String new_header_text = "The shopping list of: " + customer_name + ", at: " + store_name;
        header.setText(new_header_text);
    }

    public void cancel(View view) {
        Intent i = new Intent(S10.this, S6.class);
        startActivity(i);
        finish();
    }
}