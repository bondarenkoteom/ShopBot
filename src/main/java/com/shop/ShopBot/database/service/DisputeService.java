package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.model.Dispute;
import com.shop.ShopBot.database.model.Message;
import com.shop.ShopBot.database.repository.DisputeRepository;
import com.shop.ShopBot.database.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisputeService {

    @Autowired
    private DisputeRepository disputeRepository;

    public void save(Dispute dispute) {
        disputeRepository.save(dispute);
    }

    public List<Dispute> getDisputeMessages(Long purchaseId) {
        return disputeRepository.findDisputeMessages(purchaseId);
    }
}
