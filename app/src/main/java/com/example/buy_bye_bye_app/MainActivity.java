package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        move_to_register_seller_window();
        move_to_register_customer_window();
    }

    private void move_to_register_seller_window() {
        TextView nextbutton = (TextView) findViewById(R.id.textView_seller_register);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, S1.class));
            }
        });
    }

    private void move_to_register_customer_window() {
        TextView nextbutton = (TextView) findViewById(R.id.textView_customer_register);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, C1.class));
            }
        });
    }
}