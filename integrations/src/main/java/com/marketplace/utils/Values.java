package com.marketplace.utils;

import java.util.List;

public class Values {

    public static boolean isNotEmpty(Object o) {
        if (o instanceof List) {
            return !((List<?>) o).isEmpty();
        } else if (o instanceof String) {
            return !((String) o).isEmpty();
        } else {
            return o != null;
        }
    }

    public static Long toLong(String strNum) {
        Long result = null;
        try {
            result = Long.parseLong(strNum);
        } catch (NumberFormatException ignored) {

        }
        return result;
    }
}
