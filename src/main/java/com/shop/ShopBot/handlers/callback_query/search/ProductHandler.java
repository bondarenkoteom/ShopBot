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
import com.shop.ShopBot.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
@BotCommand(command = "PRODUCT .*", type = MessageType.CALLBACK_QUERY)
public class ProductHandler extends AbstractBaseHandler {
    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        String searchQuery = keys.get("q");

        int itemPosition = Integer.parseInt(keys.get("i"));

        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 2;

        Page<Product> products;
        if (searchQuery.equals("all")) {
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
            products = productService.findAllProducts(pageable);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, "rank");
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, sort);
            products = productService.fullTextSearch(searchQuery, pageable);
        }

        if (products.isEmpty()) return;

        List<Product> originalProducts = products.getContent();

        if (originalProducts.get(itemPosition) != null) {
            Product currentProduct = originalProducts.get(itemPosition);
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

            Map<String, String> pagination = SimplePagination.floatingItemPagination(products, searchQuery, itemPosition, SendMethod.EDIT_MEDIA, "PRODUCT");

            Buttons.Builder builder = Buttons.newBuilder().setButtonsHorizontal(pagination);
            if (!Long.valueOf(payload.getChatId()).equals(currentProduct.getOwnerId())) {
                builder.setButtonsHorizontal(button);
            }
            builder.setGoBackButton("SEARCH -p %s -m %s -q '%s'".formatted(pageNumber, SendMethod.DELETE, searchQuery));
            payload.setKeyboardMarkup(builder.build());
            bot.process(payload);
        }

    }

    private String getFormattedTextMessage(Product product) {
        return MessageText.PRODUCT.text().formatted(
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                userService.getUser(product.getOwnerId()).getUsername(),
                product.getRatingGood(), product.getRatingBad()
        );
    }
}
