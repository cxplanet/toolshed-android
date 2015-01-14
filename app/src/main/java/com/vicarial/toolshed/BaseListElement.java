package com.vicarial.toolshed;

import android.graphics.drawable.Drawable;

/**
 * Created by jayl on 1/10/15.
 */
public class BaseListElement {

    private Drawable icon;
    private String text1;
    private String text2;
    private int requestCode;

    public BaseListElement(Drawable icon, String text1, String text2, int requestCode) {
        super();
        this.icon = icon;
        this.text1 = text1;
        this.text2 = text2;
        this.requestCode = requestCode;
    }
}
