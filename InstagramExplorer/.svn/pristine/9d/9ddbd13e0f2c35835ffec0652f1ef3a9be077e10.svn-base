package com.example.duthientan.searchimagetool;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class SettingActivity extends PreferenceActivity
{
    boolean[] arrKey = {false,false,false,false,false};
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id ==android.R.id.home){
            arrKey[0] = preferences.getBoolean("twitter",false);
            arrKey[1] = preferences.getBoolean("flickr",false);
            arrKey[2] = preferences.getBoolean("instargam",false);
            arrKey[3] = preferences.getBoolean("px",false);
            arrKey[4] = preferences.getBoolean("image",false);
            Intent returnIntent =  new Intent();
            returnIntent.putExtra("result",arrKey);
            setResult(RESULT_OK,returnIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
