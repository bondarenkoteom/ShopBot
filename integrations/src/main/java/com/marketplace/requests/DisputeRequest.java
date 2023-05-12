package com.marketplace.requests;

import com.marketplace.entity.DisputeChannel;
import com.marketplace.entity.DisputeMessage;
import com.marketplace.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DisputeRequest {
    DisputeMessage disputeMessage;
    DisputeChannel disputeChannel;

    public DisputeRequest(String message, User sender, User receiver, Long channelId, String channelName, String channelDescription) {
        DisputeMessage disputeMessage = new DisputeMessage();
        disputeMessage.setMessage(message);
        disputeMessage.setSender(sender.getUsername());
        disputeMessage.setReceiver(receiver.getUsername());
        disputeMessage.setChannelId(channelId);
        disputeMessage.setDate(LocalDateTime.now());

        DisputeChannel disputeChannel = new DisputeChannel();
        disputeChannel.setId(channelId);
        disputeChannel.setName(channelName);
        disputeChannel.setDescription(channelDescription);

        this.disputeMessage = disputeMessage;
        this.disputeChannel = disputeChannel;
    }
}
