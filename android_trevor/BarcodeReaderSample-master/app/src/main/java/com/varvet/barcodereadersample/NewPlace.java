package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NewPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        final Context context = this;

        Intent intent = getIntent();
        final String message = intent.getStringExtra("qrvalue");
        final String encoded_string = intent.getStringExtra("encodes_value");
        Log.d("encodes_value",encoded_string);
        String[] new_splitted = message.split("/");

        final String submap_name = new_splitted[0];
        final int start_point = Integer.parseInt(new_splitted[1]);
        final TextView mTextView = findViewById(R.id.response_path);
        Button button = findViewById(R.id.new_place_button);

//        // use server here to get the string and encodes
//        //                                    // send html request and get back result
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://35.237.145.71/getAdaptors?map=" + submap_name;
//        // String url = "http://35.237.145.71/?input=helloworld";
//
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the first 500 characters of the response string.
//                // Log.d("response is",response);
//                                    mTextView.setText(response.substring(0, 500));
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            mTextView.setText("That didn't work!");
//                        }
//                    });
//                    // Add the request to the RequestQueue.
//                    queue.add(stringRequest);
//        while(mTextView.getText().length()<20) {}


// ...

//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://35.237.145.71/getAdaptors?map=walmart";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
//            }
//        });
//        Log.d("mTextView_2",mTextView.getText().toString());
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//        Log.d("mTextView",mTextView.getText().toString());
//
//
//                String encoded_string = mTextView.getText().toString();
                String[] encoded = encoded_string.split(";");
                Log.d("test4",encoded[0]);
                final String[] encodes = new String[encoded.length];
                final String[] decodes = new String[encoded.length];
                for(int i=0; i<encoded.length; i++){
                    String[] current_split = encoded[i].split(",");
                    encodes[i] = current_split[0];
                    if(current_split.length==1) Log.d("Value of current split",current_split[0]);
                    decodes[i] = current_split[1];
                }

        String[] places = decodes;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, places);
        final AutoCompleteTextView autoTextView = findViewById(R.id.autoCompleteTextView);

        autoTextView.setThreshold(1);
        autoTextView.setAdapter(adapter);

        autoTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                autoTextView.setText("");
            }
        });

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String user_input_string = autoTextView.getText().toString();

                Log.d("user_input_string", user_input_string);

                int end_point = 0;

                for (int i = 0; i < decodes.length; i++) {
                    if (decodes[i].toLowerCase().equals(user_input_string.toLowerCase())) {
                        end_point = Integer.parseInt(encodes[i]);
                        break;
                    }
                }

                if(end_point==0){
                    autoTextView.setText("Invalid, please choose from drop down menu");
                }

                else {

                    Log.d("endpoint", end_point + "");

                    TextView debug_view = findViewById(R.id.debug);
                    debug_view.setText(user_input_string);

                    // Start NewActivity.class
                    Intent myIntent = new Intent(NewPlace.this,
                            NavigationActivity.class);
                    myIntent.putExtra("nav_value", submap_name + "/" + start_point + "/" + end_point);
                    startActivity(myIntent);
                }
            }
        });


    }
}
