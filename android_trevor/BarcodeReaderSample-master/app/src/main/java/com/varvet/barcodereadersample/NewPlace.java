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

        Button button = findViewById(R.id.new_place_button);

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

                TextView debug_view = findViewById(R.id.debug);
                debug_view.setText(user_input_string);

                // Start NewActivity.class
                Intent myIntent = new Intent(NewPlace.this,
                        NavigationActivity.class);
                startActivity(myIntent);
            }
        });


    }
}
