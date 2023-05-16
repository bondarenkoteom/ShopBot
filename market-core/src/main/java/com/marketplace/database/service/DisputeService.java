package com.marketplace.database.service;

import com.marketplace.database.r2dbc.model.DisputeChannel;
import com.marketplace.database.r2dbc.model.DisputeMessage;
import com.marketplace.database.r2dbc.repository.DisputeChannelRepository;
import com.marketplace.database.r2dbc.repository.DisputeMessageRepository;
import com.marketplace.requests.DisputeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DisputeService {

    @Autowired
    private DisputeMessageRepository disputeMessageRepository;

    @Autowired
    private DisputeChannelRepository disputeChannelRepository;

    public Mono<DisputeMessage> save(DisputeRequest disputeRequest) {
        DisputeChannel disputeChannel = new DisputeChannel();
        disputeChannel.setId(disputeRequest.getDisputeChannel().getId());
        disputeChannel.setName(disputeRequest.getDisputeChannel().getName());
        disputeChannel.setDescription(disputeRequest.getDisputeChannel().getDescription());
        disputeChannel.isNew(true);
        disputeChannel.setUnread(1);

        DisputeMessage disputeMessage = new DisputeMessage();
        disputeMessage.setMessage(disputeRequest.getDisputeMessage().getMessage());
        disputeMessage.setSender(disputeRequest.getDisputeMessage().getSender());
        disputeMessage.setReceiver(disputeRequest.getDisputeMessage().getReceiver());
        disputeMessage.setChannelId(disputeRequest.getDisputeMessage().getChannelId());
        disputeMessage.setDate(disputeRequest.getDisputeMessage().getDate());

        return disputeChannelRepository.findById(disputeChannel.getId()).flatMap(dc -> {
            disputeChannel.isNew(false);
            disputeChannel.setUnread(dc.getUnread() + 1);
            return disputeChannelRepository.save(disputeChannel)
                    .flatMap(channel -> disputeMessageRepository.save(disputeMessage));
        }).switchIfEmpty(
                disputeChannelRepository.save(disputeChannel)
                    .flatMap(channel -> disputeMessageRepository.save(disputeMessage))
        );
    }

    public Flux<DisputeMessage> getDisputeMessages(Long purchaseId) {
        return disputeMessageRepository.findByChannelId(purchaseId);
    }
}
