package com.example.buy_bye_bye_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class C4_pop_up extends AppCompatActivity {

    String store_name;
    String product_name;

    String product_price;
    String quantity;

    TextView product_name_textView;
    TextView product_price_textView;

    TextView amount;


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

        // save the amount textView for using later in plus & minus buttons
        amount = (TextView) findViewById(R.id.C4_pop_up_Amount);

        // code for buttons
        button_plus_action(); // plus ("+")
        button_minus_action(); // minus ("-")
        button_done_action(); // Done
    }

    private void button_plus_action(){
        Button plus = (Button)findViewById(R.id.C4_pop_up_Button_Plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(quantity.split("\\s+")[0]); // example "12 pieces" --> "12"
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


    private void button_minus_action(){
        Button minus = (Button) findViewById(R.id.C4_pop_up_Button_Minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curr_amount = Integer.parseInt(amount.getText().toString());

                // can minus one just if number bigger than 0...
                if(curr_amount > 0) {
                    curr_amount--;
                    amount.setText(String.valueOf(curr_amount));
                }
                else {
                    Toast.makeText(C4_pop_up.this, "You cannot buy less than 0 units...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void button_done_action()
    {
        Button done = (Button) findViewById(R.id.C4_pop_up_Button_Done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userEmail = user.getEmail().toString();
                String userEmailWithoutDot = userEmail.replace(".", "_");

                if(Integer.parseInt(amount.getText().toString()) > 0) {
                    database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products").child(product_name).child("Name").setValue(product_name);
                    database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products").child(product_name).child("Price").setValue(product_price.replace("$", ""));
                    database.getReference("Orders").child("Carts").child(userEmailWithoutDot).child("Products").child(product_name).child("Amount").setValue(amount.getText().toString());
                }
                else {
                    Toast.makeText(C4_pop_up.this, "You cannot add to cart 0 units...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}