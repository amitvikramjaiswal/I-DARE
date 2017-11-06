package com.opensource.app.idare.utils;

import java.util.List;

public class ExpandableMenu {

    private String menuItemName;
    private List<String> childItem;
    private int menuItemImage;

    public ExpandableMenu(String menuItemName, List<String> childItem, int menuItemImage) {
        this.menuItemName = menuItemName;
        this.childItem = childItem;
        this.menuItemImage = menuItemImage;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public List<String> getChildItem() {
        return childItem;
    }

    public void setChildItem(List<String> childItem) {
        this.childItem = childItem;
    }

    public int getMenuItemImage() {
        return menuItemImage;
    }

    public void setMenuItemImage(int menuItemImage) {
        this.menuItemImage = menuItemImage;
    }
}
