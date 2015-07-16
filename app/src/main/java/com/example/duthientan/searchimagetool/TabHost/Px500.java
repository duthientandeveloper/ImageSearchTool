package com.example.duthientan.searchimagetool.TabHost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.duthientan.searchimagetool.Adapter.CustomAdapter;
import com.example.duthientan.searchimagetool.Backend.ImageFile;
import com.example.duthientan.searchimagetool.R;

/**
 * Created by Du Thien Tan on 7/14/2015.
 */
public class Px500  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_host_fragment,container,false);
        CustomAdapter adapter = new CustomAdapter(ImageFile.getBitmap("Px"));
        GridView gridView = (GridView)view.findViewById(R.id.image_off);
        gridView.setAdapter(adapter);
        return view;
    }
}
