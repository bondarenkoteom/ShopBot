package com.shop.ShopBot.handlers.input_message;

import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.handlers.callback_query.search.SearchHandler;
import com.shop.ShopBot.handlers.callback_query.user_settings.UserSettingsCommandHandler;
import com.shop.ShopBot.handlers.callback_query.vendor_panel.VendorPanelCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class UserMessageHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {

        Message message = update.getMessage();
        Trigger trigger = userService.getWaitFor(message.getFrom().getId());

        switch (trigger) {
            case USERNAME -> {
                User user = userService.getUser(message.getFrom().getId());
                user.setUsername(message.getText());
                user.setWaitFor(Trigger.UNDEFINED);
                userService.save(user);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully.""");
                bot.process(payload);

                UserSettingsCommandHandler userSettingsCommandHandler = context.getBean(UserSettingsCommandHandler.class);
                update.getMessage().setText("USER_SETTINGS -m SEND_MESSAGE");
                userSettingsCommandHandler.handle(update);
            }
            case NEW_PRODUCT_IMAGE -> {
                productService.deleteAllEditing();

                String fileId = message.getDocument() == null ? message.getPhoto().get(1).getFileId() : message.getDocument().getFileId();

                Product product = new Product();
                product.setImageId(fileId);
                product.setOwnerId(message.getFrom().getId());
                product.setEditing(true);
                product.setStatus(ProductStatus.NOT_ACTIVE);
                productService.save(product);

                setTriggerValue(update, Trigger.NEW_PRODUCT_NAME);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The image was set successfully.

                        Please enter the short and clear name of your lot. Try not to use more than 100 symbols.""");
                bot.process(payload);
            }
            case EDIT_PRODUCT_IMAGE -> {
                String fileId = message.getDocument() == null ? message.getPhoto().get(0).getFileId() : message.getDocument().getFileId();

                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setImageId(fileId);
                product.setEditing(false);
                productService.save(product);

                returnTriggerValue(update);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The image was set successfully.""");
                bot.process(payload);
            }
            case NEW_PRODUCT_NAME -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setProductName(message.getText());
                productService.save(product);

                setTriggerValue(update, Trigger.NEW_PRODUCT_DESCRIPTION);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                        payload.setText("""
                        👍 The value was set successfully.

                        Please enter the description of your lot. Try not to use more than 2500 symbols.""");
                bot.process(payload);
            }
            case EDIT_PRODUCT_NAME -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setProductName(message.getText());
                product.setEditing(false);
                productService.save(product);

                returnTriggerValue(update);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully.""");
                bot.process(payload);
            }
            case NEW_PRODUCT_DESCRIPTION -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setDescription(message.getText());
                productService.save(product);

                setTriggerValue(update, Trigger.NEW_PRODUCT_PRICE);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully.

                        Please enter the price of one key of your lot in BTC. For example  0.0001. Min. value is 0.00001""");
                bot.process(payload);
            }
            case EDIT_PRODUCT_DESCRIPTION -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setDescription(message.getText());
                product.setEditing(false);
                productService.save(product);

                returnTriggerValue(update);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully.""");
                bot.process(payload);
            }
            case NEW_PRODUCT_PRICE -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setPrice(Double.parseDouble(message.getText()));
                productService.save(product);

                setTriggerValue(update, Trigger.NEW_PRODUCT_ITEMS);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                        payload.setText("""
                        👍 The value was set successfully.

                        Send text file with items you want to sell""");
                bot.process(payload);
            }
            case EDIT_PRODUCT_PRICE -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());
                product.setPrice(Double.parseDouble(message.getText()));
                product.setEditing(false);
                productService.save(product);

                returnTriggerValue(update);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully.""");
                bot.process(payload);
            }
            case NEW_PRODUCT_ITEMS -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());

                if (message.getDocument() != null) {
                    String filePath = telegramApiClient.getFilePath(message.getDocument().getFileId());
                    byte[] bytea = telegramApiClient.getDownloadFile(filePath);
                    product.setItems(new String(bytea).split("\n"));
                } else {
                    product.setItems(message.getText().split("\n"));
                }

                product.setEditing(false);
                productService.save(product);

                returnTriggerValue(update);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully. (items)
                                                
                        Great! Your new lot has been created successfully! To Activate your lot please go to "Manage my lots" section and set appropriate lot status.""");
                bot.process(payload);

                VendorPanelCommandHandler vendorPanelCommandHandler = context.getBean(VendorPanelCommandHandler.class);
                update.getMessage().setText("VENDOR_PANEL -m SEND_MESSAGE");
                vendorPanelCommandHandler.handle(update);
            }
            case EDIT_PRODUCT_ITEMS -> {
                Product product = productService.getEditingProductByOwnerId(message.getFrom().getId());

                if (message.getDocument() != null) {
                    String filePath = telegramApiClient.getFilePath(message.getDocument().getFileId());
                    byte[] bytea = telegramApiClient.getDownloadFile(filePath);
                    product.setItems(new String(bytea).split("\n"));
                } else {
                    product.setItems(message.getText().split("\n"));
                }

                product.setEditing(false);
                productService.save(product);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The value was set successfully. (items)""");
                bot.process(payload);
            }

            default -> {
                SearchHandler searchHandler = context.getBean(SearchHandler.class);
                String searchQuery = update.getMessage().getText();
                update.getMessage().setText("SEARCH -p 0 -m %s -q '%s'".formatted(SendMethod.SEND_MESSAGE, searchQuery));
                searchHandler.handle(update);
            }
        }
    }

}
