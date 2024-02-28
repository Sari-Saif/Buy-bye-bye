package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Pop-up activity class C4_pop_up for handling product quantity adjustments and adding them to the cart.
 */
public class C4_pop_up extends AppCompatActivity {

    // Store and product details
    String store_name;
    String product_name;
    String product_price;
    String quantity;

    // TextViews for displaying product name, price, and quantity
    TextView product_name_textView;
    TextView product_price_textView;
    TextView amount;
    private ImageView hold_img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c4_pop_up);

        // Retrieve product details passed from the previous activity
        store_name = getIntent().getStringExtra("store_name");
        product_name = getIntent().getStringExtra("product_name");
        product_price = getIntent().getStringExtra("product_price");
        quantity = getIntent().getStringExtra("quantity");


        // Load the product image using Picasso
        hold_img = findViewById(R.id.c4_pop_item_image);
        Picasso.get().load(getIntent().getStringExtra("imageURL")).into(hold_img);

        // Initialize and set TextViews for product name and price
        product_name_textView = (TextView) findViewById(R.id.C4_pop_up_ProductName);
        product_name_textView.setText(product_name);

        product_price_textView = (TextView) findViewById(R.id.C4_pop_up_ProductPrice);
        product_price_textView.setText(product_price);

        // Reference to the TextView displaying the current product quantity selected
        amount = (TextView) findViewById(R.id.C4_pop_up_Amount);

        // Setup button click listeners
        button_plus_action(); // Handles increasing product quantity
        button_minus_action(); // Handles decreasing product quantity
        button_done_action(); // Confirms the addition of the product to the cart
        button_cancel_action(); // Cancels the operation
    }
    /**
     * Increases the product quantity by 1, not exceeding the available stock.
     */
    private void button_plus_action(){
        Button plus = (Button)findViewById(R.id.C4_pop_up_Button_Plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(quantity.split("\\s+")[0]); // Extracts numerical quantity from a string like "12 pieces"
                int curr_amount = Integer.parseInt(amount.getText().toString());

                // add 1 if the total quantity is grater or equal to amount
                if(curr_amount < q) {
                    curr_amount++;
                    amount.setText(String.valueOf(curr_amount));
                }
                else {
                    Toast.makeText(C4_pop_up.this, "It is not possible to buy more than the quantity in stock.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * Decreases the product quantity by 1, ensuring it does not go below 0.
     */
    private void button_minus_action(){
        Button minus = (Button) findViewById(R.id.C4_pop_up_Button_Minus);
        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int curr_amount = Integer.parseInt(amount.getText().toString());

                // can minus one just if number bigger than 0...
                if(curr_amount > 0)
                {
                    curr_amount--;
                    amount.setText(String.valueOf(curr_amount));
                }
                else
                {
                    Toast.makeText(C4_pop_up.this, "You cannot buy less than 0 units...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * Confirms the addition of the product with the specified quantity to the user's cart.
     */
    private void button_done_action()
    {
        Button done = (Button) findViewById(R.id.C4_pop_up_Button_Done);
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userEmail = user.getEmail().toString();
                String userEmailWithoutDot = userEmail.replace(".", "_");

                if(Integer.parseInt(amount.getText().toString()) > 0) {
                    // Updates the cart in Firebase with the selected product details
                    database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products").child(product_name).child("Name").setValue(product_name);
                    database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products").child(product_name).child("Price").setValue(product_price.replace("$", ""));
                    database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products").child(product_name).child("Amount").setValue(amount.getText().toString());
                    finish();// Closes the pop-up

                }
                else
                {
                    Toast.makeText(C4_pop_up.this, "You cannot add to cart 0 units...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * Cancels the operation and closes the pop-up.
     */
    private  void  button_cancel_action()
    {
        Button button =(Button) findViewById(R.id.C4_pop_up_Button_Cancel);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
