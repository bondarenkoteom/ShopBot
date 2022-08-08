package com.shop.ShopBot.constant;

public enum MessageEnum {

    START_MESSAGE("\uD83D\uDC4B Welcome to @somenamebot \n" +
            "\n" +
            "Here we exchange digital goods for BCH or vice versa. For buying you need to load some BCH to the Wallet. " +
            "Then go to the Store and choose the lot to buy. If you want to sell something just create a new lot in " +
            "Settings section. See more info at Support section! Enjoy!"),
    HELP_MESSAGE("\uD83D\uDC4B Hello this is @somenamebot ! \n" +
            "\n" +
            "Here you can exchange your digital goods for BCH tokens or vice versa.\n" +
            "Somebody who exchanges the good for the token we call \"Seller\".\n" +
            "Somebody who exchanges the token for the good we call \"Buyer\".\n" +
            "\n" +
            "For buying the good you need to load some BCH to the Wallet. Then go to the Store and choose the lot to buy. \n" +
            "If you want to sell something just create a new lot in Settings section.\n" +
            "\n" +
            "Please see the detailed info below."),
    UNKNOWN_MESSAGE("Unknown message"),
    WALLET_DEFAULT_MESSAGE("Current system currency is BCH (Bitcoin Cash)\n" +
            "\n" +
            "Your wallet balance:\n" +
            "0.000000BCH\n" +
            "Total loaded:\n" +
            "0.000000BCH\n" +
            "Total spent:\n" +
            "0.000000BCH\n" +
            "Total received:\n" +
            "0.000000BCH"),
    SETTINGS_DEFAULT_MESSAGE("Please choose the option"),
    BUYER_DEFAULT_MESSAGE("Please choose the option"),
    VENDOR_DEFAULT_MESSAGE("Please choose the option");

    private String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
