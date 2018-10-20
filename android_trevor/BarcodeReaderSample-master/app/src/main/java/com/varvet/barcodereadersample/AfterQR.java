package com.varvet.barcodereadersample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AfterQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_capture);

        Intent intent = getIntent();
        String message = intent.getStringExtra("qrvalue");

        TextView debug = findViewById(R.id.intentdebug);
        debug.setText(message);

        Button btn_save = findViewById(R.id.save_place);
        Button btn_start = findViewById(R.id.choose_nav);

        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(AfterQR.this,
                        SaveActivity.class);
                startActivity(myIntent);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(AfterQR.this,
                        StartActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
