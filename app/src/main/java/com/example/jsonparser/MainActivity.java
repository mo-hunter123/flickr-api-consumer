package com.example.jsonparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String JSON_LINK = "https://www.flickr.com/services/feeds/photos_public.gne?format=json&tags=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.button);
        EditText tagInput = (EditText) findViewById(R.id.editTextTextTagName);


        // asynchronous task to make a get request to our flickr api
        // when we get our item list we'll make a list of items
        // using the custom adapter used
        class JSONTask extends AsyncTask<String, Void, List<FlickItem>>{
            @Override
            protected List<FlickItem> doInBackground(String... strings) {
                List<FlickItem> flickItems = new ArrayList<FlickItem>();
                try {
                    flickItems = FlickItem.readItems(new URL(strings[0]), getApplicationContext());

                } catch (IOException e) {
                    System.out.println("==IO" + e);
                }catch (JSONException e){
                    System.out.println("==JSON" + e);
                }

                return flickItems;
            }

            @Override
            protected void onPostExecute(List<FlickItem> flickItems) {
                super.onPostExecute(flickItems);
                final ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(new CustomAdapter(getApplicationContext(), flickItems));
            }
        }

        // when clicking on the button we'll use the tag name to search images related to the given tag
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute(JSON_LINK + tagInput.getText());
                tagInput.setText("");
            }
        });
    }
}