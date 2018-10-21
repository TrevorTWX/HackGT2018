package com.varvet.barcodereadersample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class NewPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("qrvalue");
        String[] new_splitted = message.split("/");
        final String submap_name = new_splitted[0];
        final int start_point = Integer.parseInt(new_splitted[1]);

        Button button = findViewById(R.id.new_place_button);

        // use server here to get the string and encodes
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

        String[] places = {"dairy","books","home","deli"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, places);
        final AutoCompleteTextView autoTextView = findViewById(R.id.autoCompleteTextView);

        autoTextView.setThreshold(3);
        autoTextView.setAdapter(adapter);

        autoTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                autoTextView.setText("");
            }
        });

        //Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String user_input_string = autoTextView.getText().toString();

                int end_point = Integer.parseInt(user_input_string); // need to be converted to number first

                TextView debug_view = findViewById(R.id.debug);
                debug_view.setText(user_input_string);

                // Start NewActivity.class
                Intent myIntent = new Intent(NewPlace.this,
                        NavigationActivity.class);
                myIntent.putExtra("nav_value", submap_name + "/" + start_point + "/" + end_point);
                startActivity(myIntent);
            }
        });


    }
}
