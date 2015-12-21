package com.example.duthientan.searchimagetool;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.duthientan.searchimagetool.Adapter.CustomAdapter;
import com.example.duthientan.searchimagetool.Backend.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LybraryActivity extends AppCompatActivity {
    GridView gridView ;
    CustomAdapter adapter;
    List<Bitmap>  bitmaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lybrary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bitmaps =  ImageFile.getBitmapbyKey(getIntent().getStringExtra("Key"));
        adapter = new CustomAdapter(bitmaps);
        gridView = (GridView)findViewById(R.id.library);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LybraryActivity.this);
                ImageView imageView = new ImageView(LybraryActivity.this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                imageView.setImageBitmap(bitmaps.get(position));
                builder.setView(imageView).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File[] files = (new File(Environment.getExternalStorageDirectory().toString() + "/saved_images")).listFiles();
                        List<File> list = new ArrayList<File>();
                        for (File file : files) {
                            if (file.getName().contains(getIntent().getStringExtra("Key"))) {
                                list.add(file);
                            }
                        }
                        list.get(position).delete();
                        CustomAdapter adapter = new CustomAdapter( ImageFile.getBitmapbyKey(getIntent().getStringExtra("Key")));
                        gridView.setAdapter(adapter);

                    }
                }).create();

                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lybrary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
