package com.example.duthientan.searchimagetool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.example.duthientan.searchimagetool.Adapter.Account;
import com.example.duthientan.searchimagetool.Adapter.ButtonSocialAdapter;
import com.example.duthientan.searchimagetool.Adapter.ButtonSocial;
import com.example.duthientan.searchimagetool.Utils.Sharepreferences;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static Context contextOfApplication;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Sharepreferences share;
    private ListView mListView;
    private Account mAccount;
    ImageButton FAB;
    GridView listView;
    Toolbar toolbar;
    private ButtonSocial[] mSocials;
    ButtonSocialAdapter mAdapter;

    int[] idImage = new int[]{R.drawable.twitter, R.drawable.flickr, R.drawable.instagram, R.drawable.px, R.drawable.iamge};
//    public static class MyThread extends AsyncTask<Void,Void,Void>{
//        public interface MyListener{
//            void onComplete();
//        }
//        MyListener myListener;
//
//        public MyThread(MyListener myListener) {
//            this.myListener = myListener;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            myListener.onComplete();
//
//        }
//    }
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        share = new Sharepreferences(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        FAB = (ImageButton) findViewById(R.id.imageButton);
        listView = (GridView) findViewById(R.id.list_view);
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

        mListView = (ListView)findViewById(R.id.left_listview);

//        MyThread thread = new MyThread(new MyThread.MyListener() {
//            @Override
//            public void onComplete() {
//
//            }
//        });
//        // setlistener
//        thread.execute();



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
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;
        if (id == R.id.folder) {
            Intent intent = new Intent(MainActivity.this, FolderImageActivity.class);
            startActivity(intent);
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
        mAccount = new Account(this,mSocials,idImage);
        mListView.setAdapter(mAccount);
        final List<String> list = new ArrayList<>();
        for (ButtonSocial social : mSocials) {
            if (social.isChecked())
                list.add(social.getButtonName());
        }

        mAdapter = new ButtonSocialAdapter(this, mSocials, idImage);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ImageView button = (ImageView) view.findViewById(R.id.btn_sc);
                if(button.getTag().equals("image")){
                    Intent intent = new Intent(MainActivity.this,ImageSearchOnline.class);
                    startActivity(intent);


                }else
                if(button.getTag().equals("px")||button.getTag().equals("flickr")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.custom_alertdialog, null);
                    builder.setMessage("Do you want to login for search")
                    .setView(dialoglayout)
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("Social", button.getTag().toString());
                            startActivity(intent);
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                            intent.putExtra("Social", button.getTag().toString());
                            intent.putExtra("Authention",false);
                            startActivity(intent);
                        }
                    });
                    builder.show();

                }else
                if(button.getTag().equals("twitter")){
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    intent.putExtra("Social", button.getTag().toString());
                    startActivity(intent);
                }else
                if(button.getTag().equals("instargram")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.custom_alertdialog, null);
                    builder.setMessage("Do you want to login for search")
                            .setView(dialoglayout)
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                                    intent.putExtra("Social", button.getTag().toString());
                                    startActivity(intent);
                                }
                            })
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                                    intent.putExtra("Social", button.getTag().toString());
                                    intent.putExtra("Authention", false);
                                    startActivity(intent);
                                }
                            });
                    builder.show();
                }
            }
        });
    }
}
