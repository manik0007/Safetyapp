package com.example.admin.safetyapp;

/**
 * Created by admin on 10/26/2017.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class SendEmail extends AsyncTask<String, Void, String> {

    ServerResponse delegate = null;
    String latitude="";
    StringBuilder sb = new StringBuilder();
    String line = null;
    String longitude="";
    String text="";
    String ppl[]={"manikjaiswal007@gmail.com","manikjaiswal007@gmail.com"};
    SendEmail(String i, String j)
    {
        latitude=i;
        longitude=j;
    }
    @Override
    protected String doInBackground(String... arg0) {
        for(int i=0;i<ppl.length;i++) {
            try {
                Log.d("checking", "reached do in background for fetching indivigilation details");
                String link = "http://safetyapp.000webhostapp.com/SafetyEmail.php";

                String data = URLEncoder.encode("latitude", "UTF-8")
                        + "=" + URLEncoder.encode(latitude, "UTF-8");

                data += "&" + URLEncoder.encode("longitude", "UTF-8") + "="
                        + URLEncoder.encode(longitude, "UTF-8");
                data += "&" + URLEncoder.encode("emailid", "UTF-8") + "="
                        + URLEncoder.encode(ppl[i], "UTF-8");
                Log.d("encoded", data);
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();
                Log.d("checking", "success in writing");
                BufferedReader reader = null;

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                text = sb.toString();
                Log.d("checking", text);
                text="Success in sending mail";
                wr.close();
                reader.close();

            } catch (Exception e) {
                text = text + "Exception";
                Log.d("checking", text + e);
                text="Exception";
            }
        }
        return text;

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.ServerResponds(result);
    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
