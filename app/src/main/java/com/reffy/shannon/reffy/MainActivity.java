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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText loginEmail, loginPassword;
    Button loginButton;
    Button signupButton;

    FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authentication = FirebaseAuth.getInstance();

        loginEmail = (EditText) findViewById(R.id.txtemail);
        loginPassword = (EditText) findViewById(R.id.txtpassword);
        loginButton = (Button) findViewById(R.id.btnLogin);
        signupButton = (Button) findViewById(R.id.btnSignup);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnSignup).setOnClickListener(this);
    }

    private void userLogin() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmail.setError("Email is required");
            loginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Please enter a valid email");
            loginEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            loginPassword.setError("Minimum lenght of password should be 6");
            loginPassword.requestFocus();
            return;
        }

        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, scanner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (authentication.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, scanner.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignup:
                finish();
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btnLogin:
                userLogin();
                break;
        }
    }
}
