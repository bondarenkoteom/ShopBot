package com.marketplace.utils;

import com.marketplace.constant.SendMethod;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimplePagination {

    public static Map<String, String> twoButtonsPagination(Page<?> page, String handler, String params) {
        Map<String, String> pagination = new LinkedHashMap<>();
        if (page.hasPrevious()) {
            pagination.put(("%s -p %s " + params).formatted(handler, page.getNumber() - 1), "« Previous");
        }
        if (page.hasNext()) {
            pagination.put(("%s -p %s " + params).formatted(handler, page.getNumber() + 1), "Next »");
        }
        return pagination;
    }

    public static Map<String, String> floatingItemPagination(Page<?> page, String searchQuery, Integer itemPosition, SendMethod sendMethod, String handler) {
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

    public static Map<String, String> floatingItemPagination1(Page<?> page, String searchQuery, Integer itemPosition, SendMethod sendMethod, String handler) {
        Map<String, String> pagination = new LinkedHashMap<>();
        int lastItemPosition = page.getSize() - 1;
        int firstItemPosition = 0;

        if (!page.isFirst() && !itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, 0, itemPosition - 1, sendMethod, searchQuery), "<<" + 1);
        } else if (!page.isFirst()) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, 0, firstItemPosition, sendMethod, searchQuery), "<<" + 1);
        }

        if (page.hasPrevious() && itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber() - 1, lastItemPosition, sendMethod, searchQuery), "<" + page.getNumber());
        } else if (!itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition - 1, sendMethod, searchQuery), "prev" + getItemPosition(page.getNumber(), itemPosition - 1, itemPosition));
        }

        pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition, sendMethod, searchQuery), "•" + (page.getNumber() + 1) + "•");


        if (page.hasNext() && itemPosition.equals(lastItemPosition)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber() + 1, firstItemPosition, sendMethod, searchQuery), ">" + (page.getNumber() + 2));
        } else if (!itemPosition.equals(page.getContent().size() - 1)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition + 1, sendMethod, searchQuery), "next" + getItemPosition(page.getNumber(), itemPosition, itemPosition + 1));
        }

        if (!page.isLast() && !itemPosition.equals(page.getTotalPages())) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getTotalPages() - 1, lastItemPosition, sendMethod, searchQuery), page.getTotalPages() + ">>");
        } else if (itemPosition.equals(page.getTotalPages())) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getTotalPages() - 1, firstItemPosition, sendMethod, searchQuery), page.getTotalPages() + ">>");
        }


        return pagination;
    }

    public static Map<String, String> fiveButtonsPagination(Page<?> page, SendMethod sendMethod, String handler) {

        HashMap<String, String> pagination = new LinkedHashMap<>();

        if (!page.isFirst()) {
            pagination.put("%s -p %s -m %s".formatted(handler, 0, sendMethod), "<<" + 1);
        }
        if (page.hasPrevious()) {
            pagination.put("%s -p %s -m %s".formatted(handler, page.getNumber() - 1, sendMethod), "<" + page.getNumber());
        }

        pagination.put("%s -p %s -m %s".formatted(handler, page.getNumber(), sendMethod), "•" + (page.getNumber() + 1) + "•");

        if (page.hasNext()) {
            pagination.put("%s -p %s -m %s".formatted(handler, page.getNumber() + 1, sendMethod), ">" + (page.getNumber() + 2));
        }
        if (!page.isLast()) {
            pagination.put("%s -p %s -m %s".formatted(handler, page.getTotalPages(), sendMethod), page.getTotalPages() + ">>");
        }
        return pagination;
    }

    public static Map<String, String> floatingItemPagination2(Page<?> page, String searchQuery, Integer itemPosition, SendMethod sendMethod, String handler) {
        Map<String, String> pagination = new LinkedHashMap<>();
        int lastItemPosition = page.getSize() - 1;
        int firstItemPosition = 0;

        if (getItemPosition(page.getNumber(), page.getSize(), itemPosition) > 1) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, 0, 0, sendMethod, searchQuery), "<<" + 1);
        }

        if (page.hasPrevious() && itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber() - 1, lastItemPosition, sendMethod, searchQuery), "<" + getItemPosition(page.getNumber() - 1, page.getSize(), lastItemPosition));
        } else if (!itemPosition.equals(0)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition - 1, sendMethod, searchQuery), "<" + getItemPosition(page.getNumber(), page.getSize(), itemPosition - 1));
        }

        pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition, sendMethod, searchQuery), "•" + getItemPosition(page.getNumber(), page.getSize(), itemPosition)  + "•");


        if (page.hasNext() && itemPosition.equals(lastItemPosition)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber() + 1, firstItemPosition, sendMethod, searchQuery), getItemPosition(page.getNumber() + 1, page.getSize(), firstItemPosition) + ">");
        } else if (!itemPosition.equals(page.getContent().size() - 1)) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getNumber(), itemPosition + 1, sendMethod, searchQuery), getItemPosition(page.getNumber(), page.getSize(), itemPosition + 1) + ">");
        }

        if (getItemPosition(page.getNumber(), page.getSize(), itemPosition) < page.getTotalElements()) {
            pagination.put("%s -p %s -i %s -m %s -q '%s'".formatted(handler, page.getTotalPages() - 1, getLastItemPosition(page.getTotalElements(), page.getSize()), sendMethod, searchQuery), page.getTotalElements() + ">>");
        }

        return pagination;
    }

    private static int getItemPosition(int page, Integer countOfItemsOnPage, Integer itemPosition) {
        return ((page + 1) * countOfItemsOnPage) - (countOfItemsOnPage - itemPosition - 1);

    }

    private static int getLastItemPosition(long countOfItems, Integer pageSize) {
        long result = countOfItems % pageSize;
        if (result == 0) result = pageSize;
        return (int) result - 1;
    }


}
