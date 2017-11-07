package com.opensource.app.idare.viewmodel;

import android.content.Context;

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

    @Override
    public void destroy() {

    }
}
