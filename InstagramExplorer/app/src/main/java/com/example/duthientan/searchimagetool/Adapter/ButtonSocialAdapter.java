package com.example.duthientan.searchimagetool.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.duthientan.searchimagetool.R;

/**
 * Created by Du Thien Tan on 7/9/2015.
 */
public class ButtonSocialAdapter extends BaseAdapter {
    Context mContext;
    private static LayoutInflater inflater = null;
    ButtonSocial[] mSocials;
    int[] idImg;
    int[] idImaChecked;
    ButtonShow[] show;

    public ButtonSocialAdapter(Activity activity, ButtonSocial[] socials, int[] id) {
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
        System.out.println(position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        System.out.println(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View btnView = inflater.inflate(R.layout.buton_social, null);
        ImageView button = (ImageView) btnView.findViewById(R.id.btn_sc);
        //button.setBackgroundResource(show[position].idButton);
        button.setTag(show[position].mSocial.getButtonName());
        button.setImageResource(show[position].idButton);
        return btnView;
    }

    class ButtonShow {
        ButtonSocial mSocial;
        int idButton;
    }
}
