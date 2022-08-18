package com.shop.ShopBot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebApp {

    @JsonProperty("url")
    private String url;

    public WebApp(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
