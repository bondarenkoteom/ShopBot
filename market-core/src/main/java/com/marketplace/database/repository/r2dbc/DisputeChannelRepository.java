package com.marketplace.database.repository.r2dbc;

import com.marketplace.database.model.DisputeChannel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DisputeChannelRepository extends R2dbcRepository<DisputeChannel, Long> {

}
