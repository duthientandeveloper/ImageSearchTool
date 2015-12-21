package com.example.duthientan.searchimagetool.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.duthientan.searchimagetool.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Du Thien Tan on 7/14/2015.
 */
public class ImageAdapter extends BaseAdapter {
    List<URL> mListURL = new ArrayList<URL>();
    Map<URL, Bitmap> mBitmap = new HashMap<URL, Bitmap>();
    Context mContext;
    int size;
    private static LayoutInflater inflater = null;
    private Handler mHandler = new Handler();
    ;

    public ImageAdapter(Activity activity, List<URL> list, int size) {
        this.mContext = activity;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mListURL = list;
        this.size = size;
    }

    @Override
    public int getCount() {
        return mListURL.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View mTagView = inflater.inflate(R.layout.image_search, parent, false);
        final ImageView[] mImageView = new ImageView[1];
        mImageView[0] = (ImageView) mTagView.findViewById(R.id.image_search);
        final RelativeLayout relative = (RelativeLayout) mTagView.findViewById(R.id.load);
        if (mBitmap.containsKey(mListURL.get(position))) {
            relative.setVisibility(View.INVISIBLE);
            mImageView[0].setImageBitmap(mBitmap.get(mListURL.get(position)));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpsURLConnection connection = (HttpsURLConnection) mListURL.get(position).openConnection();
                        System.out.println(mListURL.get(position));
                        connection.setConnectTimeout(30000);
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream stream = connection.getInputStream();
                        Bitmap tmp = BitmapFactory.decodeStream(stream);
                        int width = tmp.getWidth();
                        int height = tmp.getHeight();
                        float scaleWidth = ((float) size) / width;
                        float scaleHeight = ((float) size) / height;
                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleWidth, scaleHeight);
                        final Bitmap handler = Bitmap.createBitmap(tmp, 0, 0, width, height, matrix, false);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                relative.setVisibility(View.GONE);
                                mImageView[0].setImageBitmap(handler);
                            }
                        });
                        mBitmap.put(mListURL.get(position), handler);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return mTagView;
    }


}
