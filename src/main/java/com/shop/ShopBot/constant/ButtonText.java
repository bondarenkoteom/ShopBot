package com.shop.ShopBot.constant;

public enum ButtonText {

    MANAGE_LOTS("📝 Manage my lots"),
    CREATE_LOT("✏️ Create new lot"),
    GO_BACK("🔙 Go back"),
    NEXT_PAGE("Next page"),
    DELETE_LOT("🗑 Delete"),
    EDIT_LOT("📝 Edit"),
    ACTIVATE_LOT("✅ Activate"),
    DEACTIVATE_LOT("❌ Deactivate"),
    GET_LOT_ITEMS("📦 Get items (%s pcs.)"),
    SET_NAME("🏷️ Set name"),
    USER_INFO("🪪 User info"),
    WALLET_ADD("Add"),
    WALLET_HISTORY("History"),
    WALLET_WITHDRAW("Withdraw"),
    HELP_BTC("What is BTC?"),
    HELP_SELL("What can I sell?"),
    HELP_BUYER("Buyer's features"),
    PURCHASES("Purchases"),
    STATISTICS("📈 Statistics"),
    DISPUTES("⚠️ Disputes"),
    MESSAGES("💬 Messages"),
    BUY("💳 Buy");

    private String message;

    ButtonText(String message) {
        this.message = message;
    }

    public String text() {
        return message;
    }
}
