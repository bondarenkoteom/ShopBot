package com.shop.ShopBot.constant;

public enum BotButtonNameEnum {

    SELLER("Продавец"),
    CUSTOMER("Покупатель"),
    HELP("Помощь");

    private String buttonName;

    BotButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

}
