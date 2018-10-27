package com.varvet.barcodereadersample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class MultiNavigateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multinavigate);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("nav_value");
        String[] new_splitted = message.split("/");
        String submap_name = new_splitted[0];
        int start_point = Integer.parseInt(new_splitted[1]);
        int end_point = Integer.parseInt(new_splitted[2]);


        String url1 = "http://35.237.145.71/highlightpath?map=" + submap_name + "&start=" + start_point + "&end=" + end_point + "&attach=true";
        String url2 = "http://35.237.145.71/highlightpath?map=" + submap_name + "&start=" + start_point + "&end=" + end_point + "&attach=true";
        final ImageView map1 = findViewById(R.id.map1);
        final ImageView map2 = findViewById(R.id.map2);

        RequestQueue rq1 = Volley.newRequestQueue(this);
        ImageRequest ir1 = new ImageRequest(url1,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        map1.setImageBitmap(response);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error ["+error+"]");
                        // handle the error here
                    }
                });
        rq1.add(ir1);

        RequestQueue rq2 = Volley.newRequestQueue(this);
        ImageRequest ir2 = new ImageRequest(url2,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        map2.setImageBitmap(response);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error ["+error+"]");
                        // handle the error here
                    }
                });
        rq2.add(ir2);
    }
    }


