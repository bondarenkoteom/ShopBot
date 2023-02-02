package com.shop.ShopBot.utils;

import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimplePagination {

    public static Map<String, String> twoButtonsPagination(Page<?> page, String searchQuery, SendMethod sendMethod, String handler) {
        Map<String, String> pagination = new LinkedHashMap<>();
        if (page.hasPrevious()) {
            pagination.put("%s -p %s -m %s -q '%s'".formatted(handler, page.getNumber() - 1, sendMethod, searchQuery), "« Previous");
        }
        if (page.hasNext()) {
            pagination.put("%s -p %s -m %s -q '%s'".formatted(handler, page.getNumber() + 1, sendMethod, searchQuery), "Next »");
        }
        return pagination;
    }

    public static Map<String, String> floatingItemPagination(Page<Product> page, String searchQuery, Integer itemPosition, SendMethod sendMethod, String handler) {
        Map<String, String> pagination = new LinkedHashMap<>();
        int lastItemPosition = page.getSize() - 1;
        int firstItemPosition = 0;

        if (page.hasPrevious() && itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber() - 1, lastItemPosition, sendMethod, searchQuery), "« Previous");
        } else if (!itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition - 1, sendMethod, searchQuery), "« Previous");
        }

        if (page.hasNext() && itemPosition.equals(lastItemPosition)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber() + 1, firstItemPosition, sendMethod, searchQuery), "Next »");
        } else if (!itemPosition.equals(page.getContent().size() - 1)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition + 1, sendMethod, searchQuery), "Next »");
        }

        return pagination;
    }
}
