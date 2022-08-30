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
    VENDOR_DEFAULT_MESSAGE("Please choose the option"),

    WHAT_IS_BTC("BCH is a great crypto currency (CC) wich is much much better than fiat money (like dollars or something).\n" +
            "\n" +
            "If you still don't have any BCH coins yet you can get it via @iqwalletbot\n" +
            "wich is a great crypto wallet with integrated exchange service!\n" +
            "If you have got other CC, for example Bitcoin, Doge, ETH, etc. \n" +
            "you can easily exchange it to BCH at https://bitni.com (no registration or other bullshit required).\n" +
            "Just Copy & Paste the BCH address given by the @platibot at Load Wallet \n" +
            "section and coins will come directly to your bot wallet.\n"),

    WHAT_CAN_I_SELL("What can I sell? Is it legal?\n" +
            "\n" +
            "You don't actually sell, because crypto isn't real money. You just exchange your digital good for some tokens.\n" +
            "Your good can be some information, password string, Steam game key, hidden link, check or card number, any text message or photo you can send to the bot and somebody need it.\n" +
            "\n" +
            "Of course do not try to sell something illegal like child porn or drugs. This is not a darknet or something. Respect the laws of the place you are in, otherwise you can be punished.\n" +
            "\n" +
            "❗️ Anyway you take all responsibility for the legality of your deeds. Be warned!\n"),

    BUYERS_FEATURES("Buyer's features\n" +
            "\n" +
            "As a buyer you can:\n" +
            "\n" +
            "✅ Get your personal BCH address and Load your wallet by sending BCH from your external wallet to this address.\n" +
            "✅ Search a lot and Buy some in the Store.\n" +
            "✅ Set a lot and seller rating.\n" +
            "✅ Enjoy our great service.\n" +
            "\n" +
            "Commissions:\n" +
            "All commissions are paid by the Seller, but if you want withdraw BCH from your wallet we will take 1 percent commission (including BCH network fee)."),

    ADD_INFORMATION_ABOUT_PRODUCT("Enter information about product\n" +
            "\n" +
            "1. Attach a photo\n" +
            "2. description: Enter description your product\n" +
            "3. price: Enter your price\n" +
            "4. product name: Enter your product name");

    private String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
