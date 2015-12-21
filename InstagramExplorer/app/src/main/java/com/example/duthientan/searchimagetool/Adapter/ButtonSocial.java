package com.example.duthientan.searchimagetool.Adapter;

/**
 * Created by Du Thien Tan on 7/9/2015.
 */
public class ButtonSocial {
    private boolean mChecked;
    private String mButtonName;

    public boolean isChecked() {
        return mChecked;
    }

    public String getButtonName() {
        return mButtonName;
    }

    public ButtonSocial(String name,boolean checked){
        this.mButtonName = name;
        this.mChecked = checked;
    }
}
