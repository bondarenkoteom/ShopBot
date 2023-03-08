package com.marketplace.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd.MM.yy");
        return simpleDateFormat.format(date);
    }
}
