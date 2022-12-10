package com.shop.ShopBot.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleService {

    @Autowired
    PurchaseService purchaseService;

    @Scheduled(cron = "0 0 10 * * *")
    public void orderStatusUpdate() {
        System.out.println("!!!!!!!!!!! UPDATE OVERDUE ORDERS !!!!!!!!!!!");
        purchaseService.updateOverdueOrders();

    }
}
