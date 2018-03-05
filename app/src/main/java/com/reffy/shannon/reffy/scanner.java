package com.reffy.shannon.reffy;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.ScannerIntegrator;
import com.google.zxing.integration.android.ScannerResult;

public class scanner extends AppCompatActivity {

    private Button scan_Button;
    private TextView txtformat, txtcontent;
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        scan_Button = (Button)findViewById(R.id.btnScan);
        txtformat = (TextView)findViewById(R.id.scan_format);
        txtcontent = (TextView)findViewById(R.id.scan_content);

        // Initialize all the view variables.
        mBookInput = (EditText)findViewById(R.id.ISBNinput);
        mTitleText = (TextView)findViewById(R.id.txtInputMessage);
        mAuthorText = (TextView)findViewById(R.id.scan_format);
    }

    public void search(View v)
    {
        // Get the search string from the input field.
        String queryString = mBookInput.getText().toString();

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty, start a FetchBook AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new Fetch(mTitleText, mAuthorText, mBookInput).execute(queryString);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }

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
}
