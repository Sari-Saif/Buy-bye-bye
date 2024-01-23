package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class S2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);

        move_to_S3_window();
    }

    private void move_to_S3_window() {
        Button nextbutton = (Button) findViewById(R.id.button);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S2.this, S3.class));
            }
        });
    }
}