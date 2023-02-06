package com.shop.ShopBot.handlers.callback_query.vendor_panel.manage_my_lots;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

@Component
@BotCommand(command = "GET_LOT_ITEMS .*", type = MessageType.CALLBACK_QUERY)
public class GetLotItemsHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = productService.getById(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getItems() != null && product.getItems().length > 0) {
                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_DOCUMENT);
                String itemsString = String.join("\n", product.getItems());
                InputStream inputStream = IOUtils.toInputStream(itemsString, Charset.defaultCharset());
                payload.setDocument(new InputFile(inputStream, "items"));
                bot.process(payload);
            }

        }
    }

}
