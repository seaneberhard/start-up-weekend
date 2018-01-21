package com.lucyeberhard.pillproject1;



import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by sean on 1/14/18.
 */

public class TrackerPile {

    public ArrayList<Tracker> trackers;
    public Tracker top;

    public TrackerPile() {
        trackers = new ArrayList<Tracker>();
        top = null;
    }

    public void add(Tracker t) {
        trackers.add(t);
        top = t;
    }

    public static TrackerPile deserialize(String serialization) {
        return new Gson().fromJson(serialization, TrackerPile.class);
    }

    public String serialize() {
        return new Gson().toJson(this);
    }
}
