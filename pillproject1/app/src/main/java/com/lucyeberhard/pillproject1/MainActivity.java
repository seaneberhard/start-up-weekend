package com.lucyeberhard.pillproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.security.Timestamp;
import java.security.cert.CertPath;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String PILL_RESULT = "com.example.myfirstapp.RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Send button
     */

    public void getTime(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        // Do something in response

        Date myDate = new Date();

        String result = myDate.toString();

        intent.putExtra(PILL_RESULT, result);
        startActivity(intent);
    }
}

