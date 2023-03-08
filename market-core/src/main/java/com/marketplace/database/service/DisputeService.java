package com.marketplace.database.service;

import com.marketplace.database.model.Dispute;
import com.marketplace.database.repository.DisputeRepository;
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
