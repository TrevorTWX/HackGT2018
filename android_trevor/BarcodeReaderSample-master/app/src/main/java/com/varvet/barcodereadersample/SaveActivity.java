package com.varvet.barcodereadersample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class SaveActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Button btn_back = findViewById(R.id.btn_dlt);

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
