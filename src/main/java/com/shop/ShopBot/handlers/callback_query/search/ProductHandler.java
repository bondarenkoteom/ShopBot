package com.shop.ShopBot.handlers.callback_query.search;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

@Component
@BotCommand(command = "PRODUCT .*", type = MessageType.CALLBACK_QUERY)
public class ProductHandler extends AbstractBaseHandler {
    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        String searchQuery = keys.get("q");

        Sort sort = Sort.by(Sort.Direction.DESC, "rank");

        int pageNumber = Integer.parseInt(keys.get("p"));
        int previousPage = pageNumber;
        int nextPage = pageNumber;

        if (pageNumber >= 0) {
            int countOfElements = 2;
            Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, countOfElements, sort);
            LinkedList<Product> products = productService.fullTextSearch(firstPageWithTwoElements, searchQuery);

            if (products.isEmpty()) return;

            Product previousProduct = null;
            Product currentProduct = null;
            Product nextProduct = null;
            ListIterator<Product> iterator1 = products.listIterator();
            ListIterator<Product> iterator2 = products.listIterator();
            while(iterator1.hasNext()) {
                Product product = iterator1.next();
                iterator2.next();
                if (product.getId().equals(Long.parseLong(keys.get("i")))) {
                    currentProduct = product;
                    iterator1.previous();
                    previousProduct = iterator1.hasPrevious() ? iterator1.previous() : null;
                    nextProduct = iterator2.hasNext() ? iterator2.next() : null;
                    break;
                }
            }

            if (previousProduct == null && --previousPage >= 0) {
                Pageable previousPageWithTwoElements = PageRequest.of(previousPage, countOfElements, sort);
                previousProduct = productService.fullTextSearch(previousPageWithTwoElements, searchQuery).getLast();
            }

            if (nextProduct == null && ++nextPage >= 0) {
                Pageable previousPageWithTwoElements = PageRequest.of(nextPage, countOfElements, sort);
                LinkedList<Product> nextList = productService.fullTextSearch(previousPageWithTwoElements, searchQuery);
                nextProduct = nextList.isEmpty() ? null : nextList.getFirst();
            }

            if (currentProduct != null) {
                Payload payload = new Payload(update);

                if (SendMethod.valueOf(keys.get("m")).equals(SendMethod.DELETE)) {
                    payload.setSendMethod(SendMethod.DELETE);
                    bot.process(payload);
                    payload.setSendMethod(SendMethod.SEND_PHOTO);
                } else {
                    payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
                }

                payload.setText(getFormattedTextMessage(currentProduct));
                payload.setFileId(currentProduct.getImageId());

                Map<String, String> button = Map.of("BUY -i %s".formatted(currentProduct.getId()), ButtonText.BUY.text().formatted(currentProduct.getPrice()));

                Map<String, String> pagination = new LinkedHashMap<>();
                if (previousProduct != null) {
                    pagination.put("PRODUCT -p %s -i %s -m %s -q '%s'".formatted(previousPage, previousProduct.getId(), SendMethod.EDIT_MEDIA, searchQuery), "« Previous");
                }
                if (nextProduct != null) {
                    pagination.put("PRODUCT -p %s -i %s -m %s -q '%s'".formatted(nextPage, nextProduct.getId(), SendMethod.EDIT_MEDIA, searchQuery), "Next »");
                }

                Buttons.Builder builder = Buttons.newBuilder().setButtonsHorizontal(pagination);
                if (!Long.valueOf(payload.getChatId()).equals(currentProduct.getOwnerId())) {
                    builder.setButtonsHorizontal(button);
                }
                builder.setGoBackButton("SEARCH -p %s -m %s -q '%s'".formatted(pageNumber, SendMethod.DELETE, searchQuery));
                payload.setKeyboardMarkup(builder.build());
                bot.process(payload);
            }
        }
    }

    private String getFormattedTextMessage(Product product) {
        return MessageText.PRODUCT.text().formatted(
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                "/uGrinder",
                "0", "1"
        );
    }
}
