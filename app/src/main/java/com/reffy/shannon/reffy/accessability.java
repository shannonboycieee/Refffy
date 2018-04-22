package com.reffy.shannon.reffy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;


import static android.widget.TextView.*;

public class accessability extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, OnClickListener, AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeChange.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_accessability);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accessibility");



        Switch largeText = (Switch) findViewById(R.id.textSwitch);
        largeText.setChecked(false);

        if (largeText != null) {
            largeText.setOnCheckedChangeListener(this);
        }

        findViewById(R.id.blackbutton).setOnClickListener(this);

        findViewById(R.id.bluebutton).setOnClickListener(this);

            }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Toast.makeText(this, "Large text is now " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            //do stuff when Switch is ON

        } else {
            //do stuff when Switch if OFF
        }
    }

    @Override
    public void onClick(View view) {


            switch (view.getId())

            {
                default: themeChange.changeToTheme(this, themeChange.AppTheme);
                case R.id.blackbutton:

                    themeChange.changeToTheme(this, themeChange.BLACK);

                    break;

                case R.id.bluebutton:

                    themeChange.changeToTheme(this, themeChange.BLUE);

                    break;
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = adapterView.getItemAtPosition(i).toString();

        switch (i) {
            case 0:
                themeChange.changeToTheme(this, themeChange.BLUE);
                break;

            case 1:
                themeChange.changeToTheme(this, themeChange.BLACK);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
