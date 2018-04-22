package com.reffy.shannon.reffy;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.ScannerIntegrator;
import com.google.zxing.integration.android.ScannerResult;

import java.io.InputStream;
import java.util.List;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //declaring variables
    private Button scan_Button;
    private Button search_ISBN;
    Button btnGenerate;
    private TextView txtformat, txtcontent;
    public static EditText isbnInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener authStateListener;
    AssetManager assetManager;
    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;
    public static String searchIsbn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        scan_Button = (Button) findViewById(R.id.btnScan);
        search_ISBN = (Button) findViewById(R.id.btnSearch);
        txtformat = (TextView) findViewById(R.id.scan_format);
        txtcontent = (TextView) findViewById(R.id.scan_content);
        listView = (ListView) findViewById(R.id.listView);
        btnGenerate = (Button) findViewById(R.id.btnGenerate);


        // Initialize all the view variables.
        isbnInput = (EditText) findViewById(R.id.ISBNinput);


        authentication = FirebaseAuth.getInstance();
        setupFireBaseListener();




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutUs) {
            Intent aboutIntent = new Intent(this, about.class);
            startActivity(aboutIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //initiates the scanner
        if (id == R.id.nav_scanner) {
            ScannerIntegrator scanIntegrator = new ScannerIntegrator(this);
            scanIntegrator.initiateScan();
        }
        //Open the project option on the nav drawer
        else if (id == R.id.nav_projects) {
            Intent projectIntent = new Intent(this, projects.class);
            startActivity(projectIntent);
        }
        //Open the accessibility option on the nav drawer
        else if (id == R.id.nav_accessibility) {

            Intent accessibilityIntent = new Intent(this, accessability.class);
            startActivity(accessibilityIntent);


        }
        //Open the help option on the nav drawer
        else if (id == R.id.nav_help) {
            Intent helpIntent = new Intent(this, Help.class);
            startActivity(helpIntent);

        }
        //Open the feedback option on the nav drawer
        else if (id == R.id.nav_feedback) {
            Intent feedbackIntent = new Intent(this, feedback.class);
            startActivity(feedbackIntent);
        }
        //Option for user to log out
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Method to Log the user out
    private void setupFireBaseListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    Toast.makeText(Home.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        };
    }
        @Override
        protected void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

        @Override
        protected void onStop() {
        super.onStop();
        if(authStateListener !=null)
        {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
    }

        //search isbn button click
        public void search(View v){

            //storing user input
            String searchIsbn = isbnInput.getText().toString();

            if (searchIsbn.isEmpty()) {
                isbnInput.setError("Please enter an ISBN");
                isbnInput.requestFocus();
                return;
            }

            else{
            //initiating item array adapter class
            itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

            Parcelable state = listView.onSaveInstanceState();
            listView.setAdapter(itemArrayAdapter);
            listView.onRestoreInstanceState(state);

            InputStream inputStream = getResources().openRawResource(R.raw.isbn_entries);

            //initiating readFile class
            readFile csvFile = new readFile(inputStream);
            List<String[]> detailsList = csvFile.read();

            for (String[] Data : detailsList) {
                 itemArrayAdapter.add(Data);
                }
            btnGenerate.setVisibility(View.VISIBLE);
            }}

        //When scan button is clicked
        public void scan(View v)
        {
            if(v.getId()==R.id.btnScan)
            {
                ScannerIntegrator scanIntegrator = new ScannerIntegrator(this);
                scanIntegrator.initiateScan();
            }

        }

        //Checking the scan data
        public void onActivityResult(int requestCode, int resultCode, Intent intent)
        {
            ScannerResult scanningResult = ScannerIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if(scanningResult !=null)
            {
                //place the scanned isbn into the isbn entry text box
                String scanContent = scanningResult.getContents();
                isbnInput.setText(scanContent);
            }
            else
            {
                //if scan was successful
                Toast toast = Toast.makeText(getApplicationContext(),"No scan data received.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }


        //logging out
        public void FirebaseListener(){

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null){

                    }else {
                        //informaing user they are signed out and are taken back to the login page
                        Toast.makeText(Home.this, "Signed out!", Toast.LENGTH_SHORT).show();
                        Intent loginpage = new Intent(Home.this, MainActivity.class);
                        startActivity(loginpage);
                    }
                }
            };
        }


    public void GenerateRef(View view) {

        Intent generateRef = new Intent(this,Export.class);
        startActivity(generateRef);
    }
}
