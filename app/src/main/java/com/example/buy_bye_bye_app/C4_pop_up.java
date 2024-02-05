package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class C4_pop_up extends AppCompatActivity {

    String store_name;
    String product_name;

    String product_price;
    String quantity;

    TextView product_name_textView;
    TextView product_price_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c4_pop_up);

        // get variables from C4
        store_name = getIntent().getStringExtra("store_name");
        product_name = getIntent().getStringExtra("product_name");
        product_price = getIntent().getStringExtra("product_price");
        quantity = getIntent().getStringExtra("quantity");

        // update textView-s for the right spesific poduct
        product_name_textView = (TextView) findViewById(R.id.C4_pop_up_ProductName);
        product_name_textView.setText(product_name);

        product_price_textView = (TextView) findViewById(R.id.C4_pop_up_ProductPrice);
        product_price_textView.setText(product_price);
    }
}