package com.shop.ShopBot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class AppInlineKeyboardButton extends InlineKeyboardButton {

    @JsonProperty("web_app")
    private WebApp webApp;

    public AppInlineKeyboardButton(String url) {
        this.webApp = new WebApp(url);
    }

    public WebApp getWebApp() {
        return webApp;
    }

    public void setWebApp(WebApp webApp) {
        this.webApp = webApp;
    }
}
