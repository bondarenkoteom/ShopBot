package com.marketplace.database.repository.r2dbc;

import com.marketplace.database.model.DisputeMessage;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;

@Repository
public interface DisputeMessageRepository extends R2dbcRepository<DisputeMessage, Long> {

    @Query("select * from dispute_message where channel_id = $1 and date >= $2")
    Flux<DisputeMessage> findByChannelIdAndDateAfter(Long channelId, Date date);

    Flux<DisputeMessage> findByChannelId(Long channelId);
}
