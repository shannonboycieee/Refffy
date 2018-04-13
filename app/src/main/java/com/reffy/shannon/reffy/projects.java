package com.reffy.shannon.reffy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class projects extends AppCompatActivity  {

FirebaseDatabase projectDatabse;
DatabaseReference projectDatbaseReferece;
ArrayList<String> addArray = new ArrayList<String>();
ListView project_list;

    private static final String TAG = "Projects";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //data is cached and available locally when the device loses internet connection.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        projectDatabse = FirebaseDatabase.getInstance();
        projectDatbaseReferece = projectDatabse.getReference("projects");

        project_list = (ListView)findViewById(R.id.projectList);
        project_list.setEmptyView(findViewById(android.R.id.empty));

    }


      public void addProject(View view) {

        // dialogue box that appears to the user
        final EditText project_name = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new project")
                .setMessage("What do you want to call your project?")
                .setView(project_name)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(project_name.getText());

                        if (addArray.contains(task)){

                            Toast.makeText(getBaseContext(), "This project name already exsits", Toast.LENGTH_LONG).show();
                        }else if(task == null || task.trim().equals("")){
                            Toast.makeText(getBaseContext(), "Enter a name", Toast.LENGTH_SHORT).show();


                        }else {
                            addArray.add(task);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(projects.this, android.R.layout.simple_list_item_1, addArray);
                            project_list.setAdapter(adapter);
                        }

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }
}
