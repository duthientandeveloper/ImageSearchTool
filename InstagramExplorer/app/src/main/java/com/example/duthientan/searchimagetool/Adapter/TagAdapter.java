package com.example.duthientan.searchimagetool.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duthientan.searchimagetool.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Du Thien Tan on 7/14/2015.
 */
public class TagAdapter extends BaseAdapter {
    Context mContext;
    private static LayoutInflater inflater = null;
    List<String> mList = new ArrayList<String>();

    public TagAdapter(Activity activity,Set<String> set){
        this.mContext = activity;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(set !=null)
            this.mList.addAll(set);
    }

    @Override
    public int getCount() {
        return mList.size();
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
        View mTagView = inflater.inflate(R.layout.tag_search,parent,false);
        TextView mTextView = (TextView)mTagView.findViewById(R.id.tag);
        mTextView.setBackgroundResource(R.drawable.tag);
        mTextView.setText(mList.get(position));
        return mTagView;
    }
}
