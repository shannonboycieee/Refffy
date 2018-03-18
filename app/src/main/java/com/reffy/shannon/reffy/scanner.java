package com.reffy.shannon.reffy;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.ScannerIntegrator;
import com.google.zxing.integration.android.ScannerResult;

import java.io.InputStream;

public class scanner extends AppCompatActivity implements View.OnClickListener {

    private Button scan_Button;
    private Button search_ISBN;
    private TextView txtformat, txtcontent;
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener authStateListener;
    AssetManager assetManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        scan_Button = (Button)findViewById(R.id.btnScan);
        search_ISBN = (Button)findViewById(R.id.btnSearch);
        txtformat = (TextView)findViewById(R.id.scan_format);
        txtcontent = (TextView)findViewById(R.id.scan_content);

        // Initialize all the view variables.
        mBookInput = (EditText)findViewById(R.id.ISBNinput);
        mTitleText = (TextView)findViewById(R.id.txtInputMessage);
        mAuthorText = (TextView)findViewById(R.id.scan_format);

        authentication= FirebaseAuth.getInstance();
        FirebaseListener();


    }

    public void search(View v){

          }

    public void searchDisplay(String value) {

        search_ISBN.setText(value);
    }


    public void scan(View v)
    {
        if(v.getId()==R.id.btnScan)
        {
            ScannerIntegrator scanIntegrator = new ScannerIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        ScannerResult scanningResult = ScannerIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult !=null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            txtformat.setText("Format: " + scanFormat);
            txtcontent.setText("Content: "+ scanContent);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data recieved.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void FirebaseListener(){

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                }else {
                    Toast.makeText(scanner.this, "Signed out!", Toast.LENGTH_SHORT).show();
                    Intent loginpage = new Intent(scanner.this, MainActivity.class);
                    startActivity(loginpage);
                }
            }
        };
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();


    }
}
