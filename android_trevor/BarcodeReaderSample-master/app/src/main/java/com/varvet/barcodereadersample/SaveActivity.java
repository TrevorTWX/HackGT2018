package com.varvet.barcodereadersample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SaveActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Button btn_back = findViewById(R.id.btn_dlt);

//        try {
//            openFileInput("saved_location.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
            TextView debug = findViewById(R.id.debugsaved);
            debug.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


//        btn_back.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//
//                // Start NewActivity.class
//                Intent myIntent = new Intent(SaveActivity.this,
//                        SaveActivity.class);
//                startActivity(myIntent);
//
//            }
//        });


    }
}
