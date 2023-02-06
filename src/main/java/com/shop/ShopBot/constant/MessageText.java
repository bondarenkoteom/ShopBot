package com.shop.ShopBot.constant;

public enum MessageText {

    START_MESSAGE("""
            👋 Welcome to @somenamebot

            Here we exchange digital goods for BCH or vice versa. For buying you need to load some BCH to the Wallet. Then go to the Store and choose the lot to buy. If you want to sell something just create a new lot in Settings section. See more info at Support section! Enjoy!"""),
    HELP_MESSAGE("""
            👋 Hello this is @somenamebot !

            Here you can exchange your digital goods for BCH tokens or vice versa.
            Somebody who exchanges the good for the token we call "Seller".
            Somebody who exchanges the token for the good we call "Buyer".

            For buying the good you need to load some BCH to the Wallet. Then go to the Store and choose the lot to buy.\s
            If you want to sell something just create a new lot in Settings section.

            Please see the detailed info below."""),
    UNKNOWN_MESSAGE("Unknown message"),
    WALLET_DEFAULT_MESSAGE("""
            Current system currency is BCH (Bitcoin Cash)

            Your wallet balance:
            0.000000BCH
            Total loaded:
            0.000000BCH
            Total spent:
            0.000000BCH
            Total received:
            0.000000BCH"""),
    CHOOSE_OPTION("Please choose the option"),

    WHAT_IS_BTC("""
            BCH is a great crypto currency (CC) which is much much better than fiat money (like dollars or something).

            If you still don't have any BCH coins yet you can get it via @iqwalletbot
            which is a great crypto wallet with integrated exchange service!
            If you have got other CC, for example Bitcoin, Doge, ETH, etc.
            you can easily exchange it to BCH at https://bitni.com (no registration or other bullshit required).
            Just Copy & Paste the BCH address given by the @platibot at Load Wallet
            section and coins will come directly to your bot wallet.
            """),

    WHAT_CAN_I_SELL("""
            What can I sell? Is it legal?

            You don't actually sell, because crypto isn't real money. You just exchange your digital good for some tokens.
            Your good can be some information, password string, Steam game key, hidden link, check or card number, any text message or photo you can send to the bot and somebody need it.

            Of course do not try to sell something illegal like child porn or drugs. This is not a darknet or something. Respect the laws of the place you are in, otherwise you can be punished.

            ❗️ Anyway you take all responsibility for the legality of your deeds. Be warned!
            """),

    BUYERS_FEATURES("""
            Buyer's features

            As a buyer you can:

            ✅ Get your personal BCH address and Load your wallet by sending BCH from your external wallet to this address.
            ✅ Search a lot and Buy some in the Store.
            ✅ Set a lot and seller rating.
            ✅ Enjoy our great service.

            Commissions:
            All commissions are paid by the Seller, but if you want withdraw BCH from your wallet we will take 1 percent commission (including BCH network fee)."""),

    CREATE_NEW_LOT("""
            You should enter information about product

            1. Send product image
            2. Enter your product name
            3. Enter your product description
            4. Enter your price
            5. Send text file with keys (example is attached to this message)"""),

    LOT("""
            Status: %s
            
            Title: <code>%s</code>
            Description: <code>%s</code>
            Price: <code>%s</code>
            
            Lot rating:  👍 %s   👎 %s"""),

    PRODUCT("""
            <b>%s</b>
            
            Info: %s
            Price: <b>%s</b>
            
            Seller: <code>%s</code>
            
            Rating:  👍 %s   👎 %s"""),
    LOT_IS_ACTIVE("✅ Active"),
    LOT_IS_NOT_ACTIVE("❌ Not active"),
    PERSONAL_INFO("""
            <b>%s</b>
            User ID: %s
            Rating: %s
            Sells: %s
            Purchases: %s
            Disputes win: %s
            Disputes lose: %s
            """),
    USER_INFO("""
            <b>%s</b> %s
            Message: <code>/message %s text</code>
            Rating: %s
            Sells: %s
            Disputes win: %s
            Disputes lose: %s
            """);

    private String text;

    MessageText(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

}
