package com.lucyeberhard.pillproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public static final String filename = "pilldata";
    public static final String PILL_RESULT = "com.example.myfirstapp.RESULT";

    private Date[] poptimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        poptimes = new Date[4];
        try {
            Scanner in = new Scanner(openFileInput(filename));
            for (int i = 0; i < 4; i++) poptimes[i] = new Date(in.nextLong());
            in.close();
        } catch (FileNotFoundException e) {
            for (int i = 0; i < 4; i++) poptimes[i] = new Date(0);
        }
    }

    private long timeLeft() {
        return 0;
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

