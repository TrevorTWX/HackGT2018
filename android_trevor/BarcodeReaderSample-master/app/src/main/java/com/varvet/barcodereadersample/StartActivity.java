package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Context context = this;

        Intent intent = getIntent();
        final String message = intent.getStringExtra("qrvalue");
        String[] new_splitted = message.split("/");
        final int start_point = Integer.parseInt(new_splitted[1]);

        Button btn_gtSaved = findViewById(R.id.btn_gtSaved);
        Button btn_gtNew = findViewById(R.id.btn_gtNew);

        //Capture button clicks
        btn_gtSaved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String[] splitted = new String[]{};

                try {
                    FileInputStream in = openFileInput("saved_location.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(in);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    inputStreamReader.close();
                    String saved_message = sb.toString();
                    splitted = saved_message.split("/");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String submap_name = splitted[0];
                int end_point = Integer.parseInt(splitted[1]);
//
//                                    // send html request and get back result
//                    RequestQueue queue = Volley.newRequestQueue(context);
//                    String url = "http://45.77.223.113/findpath?map=" + submap_name + "&start=" + start_point + "&end=" + end_point;
//
//                    final TextView textview = findViewById(R.id.response_path);
//
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                textview.setText(response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        textview.setText("That didn't work!");
//                    }
//                });
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);
//
//                String path = textview.getText().toString();

                // Start NewActivity.class
                Intent myIntent = new Intent(StartActivity.this,
                        NavigationActivity.class);
                myIntent.putExtra("nav_value", submap_name + "/" + start_point + "/" + end_point);
                startActivity(myIntent);
            }
        });

        btn_gtNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final TextView mTextView = findViewById(R.id.response_path);
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(context);
                String url ="http://35.237.145.71/getAdaptors?map=walmart";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                mTextView.setText("Response is: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!");
                    }
                });
                Log.d("mTextView_2",mTextView.getText().toString());
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                Log.d("mTextView",mTextView.getText().toString());

                final Intent myIntent = new Intent(StartActivity.this,
                        NewPlace.class);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        String encoded_string = mTextView.getText().toString();
                        myIntent.putExtra("qrvalue", message);
                        myIntent.putExtra("encodes_value", encoded_string);
                        startActivity(myIntent);
                    }
                }, 3000);
            }
        });
    }
}
