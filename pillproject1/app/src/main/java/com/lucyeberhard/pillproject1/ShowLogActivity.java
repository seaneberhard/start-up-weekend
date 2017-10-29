package com.lucyeberhard.pillproject1;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
        String log = PreferenceManager.getDefaultSharedPreferences(this).getString("log", "");
        log = resolveStringSharedPrefsBug(log);
        ((TextView) findViewById(R.id.log_text_view)).setText(log);
    }

    private String resolveStringSharedPrefsBug(String s) {
        // See https://stackoverflow.com/questions/34472004/android-preferences-adding-unwanted-chars/36875880#36875880
        return s.replaceAll("\n\\s*","\n");
    }
}
