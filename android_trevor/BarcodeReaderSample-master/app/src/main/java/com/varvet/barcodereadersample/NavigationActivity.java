package com.varvet.barcodereadersample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }
}
