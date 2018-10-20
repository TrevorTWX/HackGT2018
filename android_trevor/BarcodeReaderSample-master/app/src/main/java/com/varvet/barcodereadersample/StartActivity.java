package com.varvet.barcodereadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn_gtSaved = findViewById(R.id.btn_gtSaved);
        Button btn_gtNew = findViewById(R.id.btn_gtNew);

        //Capture button clicks
        btn_gtSaved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(StartActivity.this,
                        NavigationActivity.class);
                startActivity(myIntent);
            }
        });

        btn_gtNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(StartActivity.this,
                        NewPlace.class);
                startActivity(myIntent);
            }
        });
    }
}
