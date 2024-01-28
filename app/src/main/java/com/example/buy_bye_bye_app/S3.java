package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class S3 extends AppCompatActivity {

    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3);

        add = (Button) findViewById(R.id.Add_Button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), S3_Pop_up.class);
                startActivity(i);
            }
        });

//        // after click on "Add" Button
//        move_to_S3_pop_window();

    }

    /*
    - "Add" Button  operation
     */
    private void move_to_S3_pop_window() {
        Button nextbutton = (Button) findViewById(R.id.Add_Button);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S3.this, S3_Pop_up.class));
            }
        });
    }

}
