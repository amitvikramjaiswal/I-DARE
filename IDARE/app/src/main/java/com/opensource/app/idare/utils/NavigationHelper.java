package com.opensource.app.idare.utils;

import android.view.Menu;

import com.opensource.app.idare.R;

/**
 * Created by akokala on 11/7/2017.
 */
public class NavigationHelper {

    public static void setItemVisibility(Menu menu) {
        menu.findItem(R.id.invite).setVisible(false);
    }
}
