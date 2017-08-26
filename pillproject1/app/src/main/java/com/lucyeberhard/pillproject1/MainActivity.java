package com.lucyeberhard.pillproject1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TIME_FORMAT = "%02d:%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countdown();
    }

    /*
    oh boy oh boy oh boy oh boy
     */
    private void countdown() {
        final TextView text = (TextView) findViewById(R.id.countdown);
        new CountDownTimer(timeLeft(), 1000) {
            @Override
            public void onTick(long l) {
                text.setText(String.format(Locale.US, TIME_FORMAT, l / (3600 * 1000), l / (60 * 1000) % 60, l / 1000 % 60));
            }

            @Override
            public void onFinish() {
                text.setText("Pop Pill");
            }
        }.start();
    }

    /*
      Time till party time.
     */
    private long timeLeft() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        long now = new Date().getTime();
        long d1 = now - pref.getLong("-1", 0) - 4*3600*1000;
        long d2 = now - pref.getLong("-4", 0) - 24*3600*1000;
        return Math.max(0, Math.max(d1, d2));
    }

    /*
     Party time!
     Called when user taps pop
     */
    public void pop(View view) {
        if (timeLeft() > 0) return;
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("-4", pref.getLong("-3", 0));
        editor.putLong("-3", pref.getLong("-2", 0));
        editor.putLong("-2", pref.getLong("-1", 0));
        editor.putLong("-1", new Date().getTime());
        editor.apply();
        countdown();
    }
}

