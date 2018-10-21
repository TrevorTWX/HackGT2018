package com.varvet.barcodereadersample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.varvet.barcodereadersample.barcode.BarcodeCaptureActivity
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.ImageRequest
import com.android.volley.Request
import com.android.volley.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mResultTextView = findViewById(R.id.result_textview)

        findViewById<Button>(R.id.scan_barcode_button).setOnClickListener {
            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    val p = barcode.cornerPoints
                    mResultTextView.text = barcode.displayValue

                    val intent = Intent(this@MainActivity,
                            AfterQR::class.java)
                    intent.putExtra("qrvalue", barcode.displayValue);
                    startActivity(intent);


//                    // send html request and get back result
//                   val queue1 = Volley.newRequestQueue(this)
//                    val urlStr = "http://45.77.223.113/?input=Trevor"
//                    val textView = findViewById<TextView>(R.id.intentdebug)
//
//                    // Request a string response from the provided URL.
//                    val stringRequest = StringRequest(Request.Method.GET, urlStr,
//                            Response.Listener<String> { response ->
//                               // Display the first 500 characters of the response string.
//                               textView.text = "Response is: ${response}"
//                          },
//                           Response.ErrorListener { textView.text = "That didn't work!" })
//                   // Add the request to the RequestQueue.
//                    queue1.add(stringRequest)




                } else
                    mResultTextView.setText(R.string.no_barcode_captured)
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
        private val BARCODE_READER_REQUEST_CODE = 1
    }
}
