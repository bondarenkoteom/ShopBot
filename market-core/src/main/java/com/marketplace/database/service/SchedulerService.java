package com.marketplace.database.service;

import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private ProductService productService;

    private PurchaseService purchaseService;

    public SchedulerService(ProductService productService, PurchaseService purchaseService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    //todo Проверить планировщик после того, как доделаем функционал диспутов
//    @Scheduled(cron = "0 0 10 * * *")
    private void updateOverdueOrdersStatusJob() {
        System.out.println("!!!!!!!!!!! UPDATE OVERDUE ORDERS !!!!!!!!!!!");
//        purchaseService.updateOrdersStatus();
    }
}
