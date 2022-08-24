package com.shop.ShopBot.entity;

import org.telegram.telegrambots.meta.api.objects.File;

public class FileResponse {
    boolean ok;
    File result;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public File getResult() {
        return result;
    }

    public void setResult(File result) {
        this.result = result;
    }
}
