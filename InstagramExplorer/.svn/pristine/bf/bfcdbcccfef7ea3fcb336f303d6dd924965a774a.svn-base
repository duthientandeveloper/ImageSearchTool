package com.example.duthientan.searchimagetool.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.duthientan.searchimagetool.R;

/**
 * Created by Du Thien Tan on 7/28/2015.
 */
public class Account extends BaseAdapter {
    Context mContext;
    private static LayoutInflater inflater = null;
    ButtonSocial[] mSocials;
    int[] idImg;
    ButtonShow[] show;
    public Account(Activity activity,ButtonSocial[] socials,int[] id){
        this.mContext = activity;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mSocials = socials;
        this.idImg = id;
        int size = 0;
        for (ButtonSocial social : socials) {
            if (social.isChecked())
                size++;
        }
        show = new ButtonShow[size];
        for(int i =0;i<size;i++)
            show[i] = new ButtonShow();
        int i=0;int j=0;
        for (ButtonSocial social : socials) {
            if (social.isChecked()) {
                show[i].mSocial=social;
                show[i].idButton = id[j];
                i++;j++;
            }
            else
                j++;
        }
    }

    @Override
    public int getCount() {
        return show.length;
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
        View view = inflater.inflate(R.layout.row_manager_account, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.logo);
        SwitchCompat switchCompat=(SwitchCompat)view.findViewById(R.id.togle);
        imageView.setImageResource(show[position].idButton);
        switchCompat.setChecked(false);
        return view;
    }

    class ButtonShow {
        ButtonSocial mSocial;
        int idButton;
    }
}
