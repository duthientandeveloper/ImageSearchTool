package com.example.duthientan.searchimagetool;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.duthientan.searchimagetool.TabHost.Flickr;
import com.example.duthientan.searchimagetool.TabHost.ImageSearch;
import com.example.duthientan.searchimagetool.TabHost.Instargam;
import com.example.duthientan.searchimagetool.TabHost.Px500;
import com.example.duthientan.searchimagetool.TabHost.Twitter;


public class FolderImageActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Twitter"), Twitter.class, null);
        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Flickr"), Flickr.class, null);
        tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Instargam"), Instargam.class, null);
        tabHost.addTab(tabHost.newTabSpec("Tab4").setIndicator("Px500"), Px500.class, null);
        tabHost.addTab(tabHost.newTabSpec("Tab5").setIndicator("ImageSearch"), ImageSearch.class, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_folder_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        do {
            if (id == android.R.id.home) {
                finish();
                break;
            }

        } while (false);
        return super.onOptionsItemSelected(item);
    }
}
