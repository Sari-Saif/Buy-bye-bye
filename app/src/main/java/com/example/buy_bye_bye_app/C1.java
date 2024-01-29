package com.example.buy_bye_bye_app;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class C1 extends AppCompatActivity {

    private EditText et_emailC;
    private EditText et_passwordC;
    private EditText et_addressC;
    private EditText et_visaC;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);

        mAuth = FirebaseAuth.getInstance();
    }

    public void sign_up(View view) {
        et_emailC = findViewById(R.id.InputUsername);
        et_passwordC = findViewById(R.id.InputPassword);
        et_addressC = findViewById(R.id.InputAddress);
        et_visaC = findViewById(R.id.InputVisa);

        Log.d("FirebaseDebug", "email : " + et_emailC.getText().toString());

        // This listener is triggered when the task fails before the onComplete listener.
        mAuth.createUserWithEmailAndPassword(et_emailC.getText().toString(), et_passwordC.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // User creation successful
                        Log.d("FirebaseDebug", "User creation successful");

                        // Store additional user information in the database
                        Customer_user user1 = new Customer_user(et_emailC.getText().toString(),
                                et_passwordC.getText().toString(), et_addressC.getText().toString(), et_visaC.getText().toString());

                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("user")
                                .child("customer").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

                        userReference.setValue(user1)
                                .addOnCompleteListener(databaseTask -> {
                                    if (databaseTask.isSuccessful()) {
                                        Log.d("FirebaseDebug", "User information stored successfully");
                                        Intent i = new Intent(C1.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        handleDatabaseError(databaseTask.getException());
                                    }
                                });
                    } else {
                        // If sign up fails, display a message to the user.
                        handleRegistrationError(task.getException());
                    }
                })
                .addOnFailureListener(this::handleFailure);
    }

    private void handleRegistrationError(Exception exception) {
        Log.e("FirebaseAuthError", "createUserWithEmailAndPassword:failure", exception);
        showErrorToast("Registration failed: " + exception.getMessage());
    }

    private void handleDatabaseError(Exception exception) {
        Log.e("FirebaseDatabaseError", "Failed to store user information", exception);
        showErrorToast("Failed to store user information: " + exception.getMessage());
    }

    private void handleFailure(Exception exception) {
        Log.e("FirebaseFailure", "Task failed", exception);
        showErrorToast("Task failed: " + exception.getMessage());
    }

    private void showErrorToast(String errorMessage) {
        // Display an error toast
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
