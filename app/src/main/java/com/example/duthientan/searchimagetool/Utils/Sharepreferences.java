package com.example.duthientan.searchimagetool.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.duthientan.searchimagetool.MainActivity;

/**
 * Created by Du Thien Tan on 7/9/2015.
 */
public class Sharepreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public Sharepreferences() {
        Context applicationContext = MainActivity.getContextOfApplication();
        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        editor = pref.edit();
    }

    public void setShared(boolean[] arr) {
        editor.putBoolean("twitter", arr[0]);
        editor.putBoolean("flickr", arr[1]);
        editor.putBoolean("instargam", arr[2]);
        editor.putBoolean("px", arr[3]);
        editor.putBoolean("image", arr[4]);
        editor.commit();
    }

    public boolean[] getShared() {
        boolean[] arrKey = new boolean[5];
        arrKey[0] = pref.getBoolean("twitter", false);
        arrKey[1] = pref.getBoolean("flickr", false);
        arrKey[2] = pref.getBoolean("instargam", false);
        arrKey[3] = pref.getBoolean("px", false);
        arrKey[4] = pref.getBoolean("image", false);
        return arrKey;
    }
}
