package com.example.duthientan.searchimagetool.TabHost;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.duthientan.searchimagetool.Adapter.CustomAdapter;
import com.example.duthientan.searchimagetool.Backend.ImageFile;
import com.example.duthientan.searchimagetool.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Du Thien Tan on 7/14/2015.
 */
public class Twitter extends Fragment {
    List<Bitmap> bitmaps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_host_fragment,container,false);
        bitmaps = ImageFile.getBitmap("Twitter");
        CustomAdapter adapter = new CustomAdapter(bitmaps);
        final GridView gridView = (GridView)view.findViewById(R.id.image_off);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                imageView.setImageBitmap(bitmaps.get(position));
                builder.setView(imageView).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File[] files = (new File(Environment.getExternalStorageDirectory().toString() + "/saved_images")).listFiles();
                        List<File> list = new ArrayList<File>();
                        for (File file : files) {
                            if (file.getName().contains("Twitter")) {
                                list.add(file);
                            }
                        }
                        list.get(position).delete();
                        CustomAdapter adapter = new CustomAdapter(ImageFile.getBitmap("Twitter"));
                        gridView.setAdapter(adapter);

                    }
                }).create();

                builder.show();
            }
        });
        return view;
    }
}
