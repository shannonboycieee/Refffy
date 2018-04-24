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

public class accessability extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeChange.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_accessability);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accessibility");


        //declaring the switch
        Switch largeText = (Switch) findViewById(R.id.textSwitch);
        //Sets the switch to off
        largeText.setChecked(false);

        //sets oncheckedchanglistener to switch
        if (largeText != null) {
            largeText.setOnCheckedChangeListener(this);
        }

        //setting on click listener's to buttons
        findViewById(R.id.blackbutton).setOnClickListener(this);
        findViewById(R.id.bluebutton).setOnClickListener(this);
        findViewById(R.id.btnDefault).setOnClickListener(this);



            }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Toast.makeText(this, "Large text is now " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();

        //if the switch is turned on
        if(isChecked) {


        } else {
            //do stuff when Switch if OFF
        }
    }

    @Override
    public void onClick(View view) {

            //switch statement to identify which button was clicked and what to do
            switch (view.getId())

            {
                default: themeChange.changeToTheme(this, themeChange.AppTheme);

                //if the black button is clicked the theme is changed to the Black theme defined in styles
                case R.id.blackbutton:

                    themeChange.changeToTheme(this, themeChange.BLACK);

                    break;
                //if the blue button is clicked the theme is changed to the blue theme defined in styles
                case R.id.bluebutton:

                    themeChange.changeToTheme(this, themeChange.BLUE);

                    break;

                //if the default button is clicked the theme is changed to the App theme defined in styles
                case R.id.btnDefault:

                    themeChange.changeToTheme(this, themeChange.AppTheme);

                    break;
            }
    }


}
