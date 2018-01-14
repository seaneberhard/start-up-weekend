package com.lucyeberhard.pillproject1;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sean on 1/14/18.
 */

public class Tracker {

    ArrayList<Pair<Integer, Long>> limits;
    ArrayList<Date> log;
    
    public Tracker() {

        limits = new ArrayList<Pair<Integer, Long>>();
        log = new ArrayList<Date>();

    }

    public void addLimit(int quantity, long time) {
        limits.add(Pair.<Integer, Long>create(quantity, time));
    }

    public void pop() {
        log.add(new Date());
    }

    public long timeLeft() {
        Date now = new Date();
        long left = 0;
        for (Pair<Integer, Long> limit : limits) {
            int quantity = limit.first;
            long time = limit.second;
            if (log.size() < quantity) {
                continue;
            }
            Date pivot = log.get(log.size() - quantity);
            long diff = (pivot.getTime() + time) - now.getTime();
            if (left < diff) {
                left = diff;
            }
        }
        return left;
    }

    public String log() {

        String str = "";
        for(Date d : log) {
            str = d.toString() + "\n" + str;
        }
        return str;

    }
}
