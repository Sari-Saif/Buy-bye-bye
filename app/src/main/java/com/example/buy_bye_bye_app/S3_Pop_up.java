package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class S3_Pop_up extends Activity {

    Button done;
    private EditText store_name;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3_pop_up);

        mAuth = FirebaseAuth.getInstance();

        move_to_S3_popUP_window();

        done_Ok();



    }

    /*
     handling pop up and set position
     */
    private void move_to_S3_popUP_window()
    {
        DisplayMetrics ds =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x= 0;
        params.y=-20;

        getWindow().setAttributes(params);
    }

    /*
        done Button - adding into data base new store details
     */
    public void done_Ok()
    {

        done = (Button) findViewById(R.id.done_button);
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAuth = FirebaseAuth.getInstance();

                store_name =(EditText) findViewById(R.id.Input_store_Name);
                String store = store_name.getText().toString();

                //TODO: ADD TO DATA BASE !!
                //TODO: set into OwnerID random Values !!
                Log.d("FirebaseDebug", "EditText value: " + store_name.getText().toString());
                database.getInstance().getReference().child("Stores").child(store).child(
                        "OwnerID").setValue(mAuth.getCurrentUser().getUid());


                // wait and ask yoad
                database.getInstance().getReference().child("Stores").child(store).child(
                        "StoreName").setValue(store_name.getText().toString());
                Intent i = new Intent(S3_Pop_up.this , S3_Pop_up.class);
                startActivity(i);

                //  after adding its done back to main page
                startActivity(new Intent(S3_Pop_up.this, S3.class));


            };
        });
    }
}