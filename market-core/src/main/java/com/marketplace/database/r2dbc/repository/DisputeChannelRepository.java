package com.marketplace.database.r2dbc.repository;

import com.marketplace.database.r2dbc.model.DisputeChannel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeChannelRepository extends R2dbcRepository<DisputeChannel, Long> {

}
