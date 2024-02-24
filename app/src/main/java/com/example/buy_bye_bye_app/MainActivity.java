package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main activity class that serves as the entry point to the application.
 * This class provides navigation options to different activities for both sellers and customers to log in or register.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the UI layout for this Activity
        setContentView(R.layout.activity_main);

        move_to_login_seller_window();
        move_to_register_seller_window();

        move_to_login_customer_window();
        move_to_register_customer_window();

    }
    /**
     * Navigates to the seller login window when the corresponding button is clicked.
     */
    private void move_to_login_seller_window() {
        Button nextbutton = (Button) findViewById(R.id.button_seller_login);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, S2.class));
            }
        });
    }
    /**
     * Navigates to the seller registration window when the corresponding text view is clicked.
     */
    private void move_to_register_seller_window() {
        TextView nextbutton = (TextView) findViewById(R.id.textView_seller_register);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, S1.class));
            }
        });
    }
    /**
     * Navigates to the customer login window when the corresponding button is clicked.
     */
    private void move_to_login_customer_window() {
        Button nextbutton = (Button) findViewById(R.id.button_customer_login);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, C2.class));
            }
        });
    }
    /**
     * Navigates to the customer registration window when the corresponding text view is clicked.
     */
    private void move_to_register_customer_window() {
        TextView nextbutton = (TextView) findViewById(R.id.textView_customer_register);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Start the customer registration activity
                startActivity(new Intent(MainActivity.this, C1.class));
            }
        });
    }
}