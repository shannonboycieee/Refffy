package com.reffy.shannon.reffy;

import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.reffy.shannon.reffy.Home.searchIsbn;


public class readFile {
    InputStream inputStream;


    public readFile(InputStream inputStream){
        this.inputStream = inputStream;

    }

    public List<String[]> read(){
        List<String[]> resultList = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] isbnArray = csvLine.split(",");

                for(String search:isbnArray){

                     if (search.equals(isbnArray)){
                         resultList.add(isbnArray);

                }


                }



                }



        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}
