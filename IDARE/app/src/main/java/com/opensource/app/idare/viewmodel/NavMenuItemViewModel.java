package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.opensource.app.idare.utils.ExpandableMenu;

public class NavMenuItemViewModel implements ViewModel {

    private Context context;
    private ExpandableMenu expandableMenu;

    public NavMenuItemViewModel(Context context, ExpandableMenu expandableMenu) {
        this.context = context;
        this.expandableMenu = expandableMenu;
    }

    public ExpandableMenu getExpandableMenu() {
        return expandableMenu;
    }

    public Drawable getMenuItemIcon() {
        return ContextCompat.getDrawable(context, expandableMenu.getMenuItemImage());
    }

    @Override
    public void destroy() {

    }
}
