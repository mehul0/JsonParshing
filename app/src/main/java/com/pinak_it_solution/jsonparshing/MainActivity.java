package com.pinak_it_solution.jsonparshing;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHit = (Button) findViewById(R.id.btnhit);
        tvJson = (TextView) findViewById(R.id.tvJsonHit);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("https://jsonplaceholder.typicode.com/posts/1");
            }
        });


    }
    public class JSONTask  extends AsyncTask<String ,String,String >{


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = " ";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson =  buffer.toString();

                JSONObject finalObject = new JSONObject(finalJson);

                int userId = finalObject.getInt("userId");
                int id = finalObject.getInt("id");
                String title = finalObject.getString("title");
                String body = finalObject.getString("body");

                return "User ID = "+userId  + "\n"+" ID = " + id +"\n" + " Title = " + title +"\n" + " Body = " + body ;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvJson.setText(s);
        }
    }
}

