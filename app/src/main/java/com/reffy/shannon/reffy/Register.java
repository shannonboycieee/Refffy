package com.reffy.shannon.reffy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity implements View.OnClickListener{

    //Variables
    private EditText userName, userPassword, userEmail;
    private Button signUp, loginButton;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        //Finding by xml id names
        userEmail = (EditText) findViewById(R.id.txtuserEmail);
        userPassword = (EditText) findViewById(R.id.txtpassword);
        userEmail = (EditText) findViewById(R.id.txtuserEmail);
        signUp = (Button) findViewById(R.id.btn_signup);
        loginButton = (Button) findViewById(R.id.btnLoginA);

        authentication = FirebaseAuth.getInstance();

        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.btnLoginA).setOnClickListener(this);

    }

    private void registerUser() {
        String email = userEmail.getText().toString().trim();
        String password = userEmail.getText().toString().trim();

        if (email.isEmpty()) {
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Please enter a valid email");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            userPassword.setError("Password is required");
            userPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            userPassword.setError("Minimum lenght of password should be 6");
            userPassword.requestFocus();
            return;
        }


        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(Register.this, MainActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                registerUser();
                break;

            case R.id.btnLoginA:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}

