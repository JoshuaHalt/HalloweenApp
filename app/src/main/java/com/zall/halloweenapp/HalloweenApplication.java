package com.zall.halloweenapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HalloweenApplication extends Application {

    SharedPreferences _sharedPrefs;
    Map<String, Integer> _pastYearCounts = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();

        _sharedPrefs = getSharedPreferences("HalloweenPrefs", Context.MODE_PRIVATE);

        for (Map.Entry<String, ?> entry : _sharedPrefs.getAll().entrySet())
            _pastYearCounts.put(entry.getKey(), (Integer) entry.getValue());
    }

    public void WriteCountForYear(int year, int count) {
        SharedPreferences.Editor editor = _sharedPrefs.edit();
        editor.putInt(Integer.toString(year), count);
        editor.apply();
    }

    public int ReadCountForYear(int year) {
        return _sharedPrefs.getInt(Integer.toString(year), 0);
    }

    public ArrayList<String> GetPastYears() {
        ArrayList<String> retval = new ArrayList<>();

        for (String s : _pastYearCounts.keySet())
            retval.add(s);

        return retval;
    }

    public void quickToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void longToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
