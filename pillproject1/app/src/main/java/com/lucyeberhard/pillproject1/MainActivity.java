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
    SharedPreferences prefs;
    TrackerPile tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String serialization = prefs.getString(getString(R.string.serialization),null);
        if (serialization == null) {
            TrackerPile tp = new TrackerPile();
            tp.add(Tracker.getDefault());
            save();
            startActivity(new Intent(this, AboutActivity.class));
        }
        else {
            tp = TrackerPile.deserialize(serialization);
            setContentView(R.layout.activity_main);
            countdown();
        }
    }

    private void save() {
        prefs.edit().putString(R.string.serialization, tp.serialize());
    }

    /*
    oh boy oh boy oh boy oh boy
     */
    private void countdown() {
        final TextView text = (TextView) findViewById(R.id.countdown);
        if (tp.top == null) return;
        new CountDownTimer(tp.top.timeLeft(), 1000) {
            @Override
            public void onTick(long l) {
                text.setText(String.format(Locale.US, "%02d:%02d:%02d", l / (3600 * 1000), l / (60 * 1000) % 60, l / 1000 % 60));
            }

            @Override
            public void onFinish() {
                text.setText(R.string.log_dose_text);
            }
        }.start();
    }

    /*
     Party time!
     Called when user taps pop
     */
    public void pop(View view) {
        if (tp.top == null || tp.top.timeLeft() > 0) return;
        tp.top.pop();
        save();
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

