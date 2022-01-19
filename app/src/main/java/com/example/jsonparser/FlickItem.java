package com.example.jsonparser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FlickItem {

    private final String title;
    private final String link;
    private final String date_taken;
    private final String media;

    public FlickItem(String title, String link, String date_taken, String media){
        this.link = link;
        this.title = title;
        this.date_taken = date_taken;
        this.media = media;
    }
    static List<FlickItem> readItems(URL url, Context context) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(loadJsonFileFromLink(context, url));
        JSONArray jsonArrayItems = jsonObject.getJSONArray("items");
        List<FlickItem> flickItems = new ArrayList<FlickItem>();

        for(int i = 0; i < jsonArrayItems.length(); i++){
            flickItems.add(
                new FlickItem(
                    jsonArrayItems.getJSONObject(i).getString("title"),
                    jsonArrayItems.getJSONObject(i).getString("link"),
                    jsonArrayItems.getJSONObject(i).getString("date_taken"),
                    jsonArrayItems.getJSONObject(i).getJSONObject("media").getString("m")
                )
            );
        }
        return flickItems;
    }
    public static String loadJsonFileFromLink(Context context, URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String body = null;
        StringBuffer str = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line).append('\n');
            }

            body = sb.toString();
            str.append(body);
            str.replace(body.length()-1, body.length(), "");
            str.replace(0, 15, "");


            Log.d("HTTP-GET", body);
        }catch (Exception e){
            System.out.println("==R==" + e);
        }

        return str.toString();
    }


    @Override
    public String toString() {
        return "FlickItem{" +
            "title='" + title + '\'' +
            ", link='" + link + '\'' +
            ", date_taken='" + date_taken + '\'' +
            ", media='" + media + '\'' +
            '}';
    }

    public String getDate_taken() {
        return date_taken;
    }

    public String getLink() {
        return link;
    }

    public String getMedia() {
        return media;
    }

    public String getTitle() {
        return title;
    }
}
