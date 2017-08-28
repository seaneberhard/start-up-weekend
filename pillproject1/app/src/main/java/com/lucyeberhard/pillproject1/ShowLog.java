package com.lucyeberhard.pillproject1;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
        ((TextView) findViewById(R.id.TextView01)).setText(PreferenceManager.getDefaultSharedPreferences(this).getString("log",""));
    }
}
