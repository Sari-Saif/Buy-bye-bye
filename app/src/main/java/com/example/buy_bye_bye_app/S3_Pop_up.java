package com.example.buy_bye_bye_app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
/**
 * This class represents a pop-up activity used for adding a new store. It extends Android's Activity class.
 * This pop-up allows the user to input a new store name and add it to the Firebase Realtime Database under the current user's ID.
 */
public class S3_Pop_up extends Activity {

    // Button to submit the new store name.
    private Button done;

    // EditText field for inputting the new store's name.
    private EditText store_name;

    // Firebase Realtime Database instance for database operations.
    private FirebaseDatabase database;

    // Firebase Authentication instance for user authentication.
    private FirebaseAuth mAuth;


    /**
     * Called when the activity is starting. This is where most initialization should go: calling setContentView(int) to inflate the activity's UI,
     * using findViewById(int) to programmatically interact with widgets in the UI, setting up any of the activity's initial fragment transactions, etc.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3_pop_up);

        // UI elements
        done = findViewById(R.id.done_button);
        store_name = findViewById(R.id.Input_store_Name);

        // Initialize Firebase instances.
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Adjusts the pop-up window's size and position.
        move_to_S3_popUP_window();
        // Sets up the functionality for the "Done" button.
        done_Ok();
    }


    /**
     * Cancels the pop-up and returns to the calling Activity.
     * @param view The view (button) that was clicked.
     */
    public void cancel_pop_up(View view) {

        // Creates an Intent to start the S3 Activity.
        Intent i = new Intent(S3_Pop_up.this , S3.class);
        startActivity(i);
        finish();// Finishes the current Activity, removing it from the stack.
    }

    /**
     * Adjusts the current window to act as a pop-up, setting its size and position on the screen.
     */
    private void move_to_S3_popUP_window()
    {
        DisplayMetrics ds =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        // Calculate width and height as a percentage of the screen size.
        int width = ds.widthPixels;
        int height = ds.heightPixels;

        // Set the window size to 80% of screen width and 70% of screen height.
        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;// Centers the pop-up.
        params.x= 0;// X position
        params.y=-20; // Y position

        getWindow().setAttributes(params);
    }

    /**
     * Sets up the done button's onClickListener. When clicked, it adds the new store details to the Firebase database under "Stores".
     */
    public void done_Ok()
    {

        done = (Button) findViewById(R.id.done_button);
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                store_name =(EditText) findViewById(R.id.Input_store_Name);
                String store = store_name.getText().toString();

                // Add the store to the database under "Stores", setting the "OwnerID" to the current user's UID.
                // Also sets the "StoreName" in the database to the value entered in the EditText.                Log.d("FirebaseDebug", "EditText value: " + store_name.getText().toString());
                database.getInstance().getReference().child("Stores").child(store).child(
                        "OwnerID").setValue(mAuth.getCurrentUser().getUid());
                database.getInstance().getReference().child("Stores").child(store).child(
                        "StoreName").setValue(store_name.getText().toString());

                // Intent to refresh the pop-up or return to the main page (S3 Activity) after adding the store.
                Intent i = new Intent(S3_Pop_up.this , S3_Pop_up.class);
                startActivity(i);

                //  after adding its done back to main page
                startActivity(new Intent(S3_Pop_up.this, S3.class));


            };
        });
    }
}