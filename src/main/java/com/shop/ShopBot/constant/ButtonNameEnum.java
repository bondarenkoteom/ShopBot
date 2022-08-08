package com.shop.ShopBot.constant;

public enum ButtonNameEnum {

    USER_SETTINGS("User settings"),
    WALLET("Wallet"),
    BUYER_PANEL("Buyer panel"),
    VENDOR_PANEL("Vendor panel");

    private String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

}
