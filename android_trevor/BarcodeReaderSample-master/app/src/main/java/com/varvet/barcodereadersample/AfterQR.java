package com.varvet.barcodereadersample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AfterQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_capture);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("qrvalue");

        TextView debug = findViewById(R.id.intentdebug);
        debug.setText(message);

        Button btn_save = findViewById(R.id.save_place);
        Button btn_start = findViewById(R.id.choose_nav);

      String[] splitted = message.split("/");

        final Context context = this;

        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String filename = "saved_location.txt";
                String fileContents = message;
                FileOutputStream outputStream;

                File file = context.getFileStreamPath(filename);
                if(file != null || file.exists()) {
                    context.deleteFile(filename);
                }

                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(fileContents.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Start NewActivity.class
                Intent myIntent = new Intent(AfterQR.this,
                        SaveActivity.class);
                myIntent.putExtra("qrvalue", message);

                startActivity(myIntent);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(AfterQR.this,
                        StartActivity.class);

              myIntent.putExtra("qrvalue", message);
                startActivity(myIntent);
            }
        });
    }
}
