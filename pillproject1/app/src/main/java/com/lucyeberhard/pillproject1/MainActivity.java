package com.lucyeberhard.pillproject1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TIME_FORMAT = "%02d:%02d:%02d";
    private static final boolean testing = false;
    private static final long fourHours = testing ? 4 * 1000 :  4 * 3600 * 1000;
    private static final long oneDay = testing ? 24 * 1000 :  24 * 3600 * 1000;

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
                text.setText(R.string.log_dose_text);
            }
        }.start();
    }

    /*
      Time till party time.
     */
    private long timeLeft() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        long now = new Date().getTime();
        long d1 = fourHours - (now - pref.getLong("-1", 0));
        long d2 = oneDay - (now - pref.getLong("-4", 0));
        return Math.max(0, Math.max(d1, d2));
    }

    /*
     Party time!
     Called when user taps pop
     */
    public void pop(View view) {
        if (timeLeft() > 0) return;
        Date now = new Date();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("-4", pref.getLong("-3", 0));
        editor.putLong("-3", pref.getLong("-2", 0));
        editor.putLong("-2", pref.getLong("-1", 0));
        editor.putLong("-1", now.getTime());
        editor.putString("log",  now.toString() + "\n" + pref.getString("log",""));
        editor.apply();
        countdown();
    }

    /*
    Called when user requests log
     */
    public void openLog(View view) {
        Intent intent = new Intent(this, ShowLogActivity.class);
        startActivity(intent);
    }

    /*
    Called when user requests about
     */
    public void comeAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}

