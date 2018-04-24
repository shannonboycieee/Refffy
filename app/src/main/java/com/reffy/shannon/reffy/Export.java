package com.reffy.shannon.reffy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Export extends AppCompatActivity implements View.OnClickListener {

    TextView generatedReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Export reference");

        generatedReference = findViewById(R.id.txtGenRef);
        findViewById(R.id.btnProjSave).setOnClickListener(this);
        findViewById(R.id.btnEmailExport).setOnClickListener(this);
        findViewById(R.id.btnTextExport).setOnClickListener(this);

        //generatedReference.setText();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEmailExport:

                String reference =generatedReference.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"shannonboyce036@hotmail.co.uk"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Reference");
                email.putExtra(Intent.EXTRA_TEXT, reference);

                //prompts email client
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                break;

            case R.id.btnTextExport:

                break;

            case R.id.btnProjSave:
                startActivity(new Intent(this, projects.class));




        }

    }
}
