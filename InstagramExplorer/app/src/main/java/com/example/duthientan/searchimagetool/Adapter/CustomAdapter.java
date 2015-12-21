package com.example.duthientan.searchimagetool.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ImageView;


import com.example.duthientan.searchimagetool.R;
import com.example.duthientan.searchimagetool.Utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Du Thien Tan on 6/23/2015.
 */
public class CustomAdapter extends BaseAdapter {

    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();


    public CustomAdapter(List<Bitmap> tmp) {
        System.out.println(tmp.size());
        bitmaps = tmp;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.image_search, parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.image_search);
        RelativeLayout bar = (RelativeLayout) view.findViewById(R.id.load);
        bar.setVisibility(View.INVISIBLE);
        imageView.setImageBitmap(bitmaps.get(position));
        return view;
    }

    @Override
    public int getCount() {

        return bitmaps.size();
    }
}
