package com.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class AppKeyboardButton extends KeyboardButton {

    @JsonProperty("web_app")
    private WebApp webApp;

    public AppKeyboardButton(String button, String url) {
        super(button);
        this.webApp = new WebApp(url);
    }

    public WebApp getWebApp() {
        return webApp;
    }

    public void setWebApp(WebApp webApp) {
        this.webApp = webApp;
    }
}
