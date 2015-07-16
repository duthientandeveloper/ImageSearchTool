package com.example.duthientan.searchimagetool.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.duthientan.searchimagetool.MainActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Du Thien Tan on 7/9/2015.
 */
public class Sharepreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public Sharepreferences(Context context) {
        Context applicationContext = context;
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

    public void setTag(String tag) {
        Set<String> setTag = pref.getStringSet("tag", null);
        if(setTag==null)
            setTag =new HashSet<String>();
        List<String> list = new ArrayList<String>();
        list.addAll(setTag);

        if(!list.contains(tag))
            list.add(tag);
        setTag = new HashSet<String>(list);
        editor.putStringSet("tag", setTag);
        editor.commit();
    }
    public void removeTag(String tag){
        Set<String> setTag = pref.getStringSet("tag", null);
        if(setTag==null)
            setTag =new HashSet<String>();
        List<String> list = new ArrayList<String>();
        list.addAll(setTag);
        if(list.remove(tag)){
            setTag = new HashSet<String>(list);
            editor.putStringSet("tag", setTag);
            editor.commit();
        }


    }

    public Set<String> getTag() {
        Set<String> tag = new HashSet<String>();
        tag = pref.getStringSet("tag", null);
        return tag;
    }
}
