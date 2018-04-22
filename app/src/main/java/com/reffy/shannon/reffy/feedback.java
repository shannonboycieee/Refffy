package com.reffy.shannon.reffy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class feedback extends AppCompatActivity {

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");



        final EditText fbName = (EditText) findViewById(R.id.feedbackName);
        final EditText fbEmail = (EditText) findViewById(R.id.feedbackEmail);
        final EditText fbMessage = (EditText) findViewById(R.id.feedbackMessage);
        final Spinner fbSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackRating);
        Button fbButton = (Button) findViewById(R.id.btnsendFeedback);

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = fbName.getText().toString();
                String emailString = fbEmail.getText().toString();
                String messageString = fbMessage.getText().toString();

                if(TextUtils.isEmpty(nameString)){

                    fbName.setError("Please enter your name");
                    fbName.requestFocus();
                    return;
                }

                Boolean onError = false;
                if(!isValidEmail(emailString)){
                    onError = true;
                    fbEmail.setError("Invalid email");
                    return;

                }

                if (TextUtils.isEmpty(messageString))
                {
                    fbMessage.setError("Please provide a response");
                    fbMessage.requestFocus();
                    return;
                }
                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"shannonboyce036@hotmail.co.uk"});
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Name:"+nameString+'\n'+"Email:"+emailString+'\n'+"Message:"+'\n'+messageString);

            /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));



            }
        });
    }

private boolean isValidEmail (String emailSting){

    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(emailSting);
    return matcher.matches();
}
}

