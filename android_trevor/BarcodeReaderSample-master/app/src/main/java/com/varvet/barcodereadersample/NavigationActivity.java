package com.varvet.barcodereadersample;

import android.graphics.Bitmap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("nav_value");
        String[] new_splitted = message.split("/");
        String submap_name = new_splitted[0];
        int start_point = Integer.parseInt(new_splitted[1]);
        int end_point = Integer.parseInt(new_splitted[2]);
        String url = "http://35.237.145.71/highlightpath?map=" + submap_name + "&start=" + start_point + "&end=" + end_point + "&attach=true";
        final ImageView map = findViewById(R.id.imageView);

        RequestQueue rq = Volley.newRequestQueue(this);
        ImageRequest ir = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        map.setImageBitmap(response);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error ["+error+"]");
                        // handle the error here
                    }
                });
        rq.add(ir);
    }
}
