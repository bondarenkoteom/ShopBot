package com.marketplace.utils;

public class PageUtils {

    public static int getGlobalPosition(int page, int itemsOnPage, int positionOnPage) {
        return (page * itemsOnPage) - (itemsOnPage - positionOnPage);
    }
}
