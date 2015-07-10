package com.example.duthientan.searchimagetool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.duthientan.searchimagetool.Adapter.ButtonSocialAdapter;
import com.example.duthientan.searchimagetool.Adapter.ButtonSocial;
import com.example.duthientan.searchimagetool.Backend.ApiSocial;
import com.example.duthientan.searchimagetool.Backend.OAuthSocial;
import com.example.duthientan.searchimagetool.Utils.Sharepreferences;

import org.scribe.builder.api.TwitterApi;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static Context contextOfApplication;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Sharepreferences share;
    ImageButton FAB;
    ListView listView;
    Toolbar toolbar;
    private ButtonSocial[] mSocials;
    ButtonSocialAdapter mAdapter;
    int[] idImage = new int[]{R.drawable.twitter,R.drawable.twitter,R.drawable.instagram,R.drawable.twitter,R.drawable.twitter};

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.provider.Settings.System.putString(getApplicationContext().getContentResolver(),android.provider.Settings.System.WIFI_STATIC_DNS1, "8.8.8.8");
        android.provider.Settings.System.putString(getApplicationContext().getContentResolver(),android.provider.Settings.System.WIFI_STATIC_DNS2, "8.8.4.4");
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);
        share = new Sharepreferences();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        FAB = (ImageButton) findViewById(R.id.imageButton);
        listView = (ListView) findViewById(R.id.list_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        setSupportActionBar(toolbar);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                share.setShared(data.getBooleanArrayExtra("result"));
                onStart();
            }
        }
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mSocials = new ButtonSocial[]{
                new ButtonSocial("twitter", share.getShared()[0]),
                new ButtonSocial("flickr", share.getShared()[1]),
                new ButtonSocial("instargram", share.getShared()[2]),
                new ButtonSocial("px", share.getShared()[3]),
                new ButtonSocial("image", share.getShared()[4])
        };
        final List<String> list = new ArrayList<>();
        for(ButtonSocial social: mSocials){
            if (social.isChecked())
                list.add(social.getButtonName());
        }
        mAdapter = new ButtonSocialAdapter(this,mSocials,idImage);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (list.get(position)){
                        case "twitter":
                    }
            }
        });
    }
}
