package com.varvet.barcodereadersample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        final TextView userInput = findViewById(R.id.new_place);

        Button button = findViewById(R.id.new_place_button);

        userInput.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                userInput.setText("");
            }
        });

        //Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String user_input_string = userInput.getText().toString();

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
