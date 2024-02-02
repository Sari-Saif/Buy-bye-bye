package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class C6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c6);

        move_to_orders_pending_window();
    }

    private void move_to_orders_pending_window(){
        Button orders_pending = (Button) findViewById(R.id.C6_OrdersPending_button);
        orders_pending.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(C6.this, C7.class));
            }
        });
    }

    public void edit_profile(View view) {
        Intent i = new Intent(C6.this, C9.class);
        startActivity(i);
    }
}