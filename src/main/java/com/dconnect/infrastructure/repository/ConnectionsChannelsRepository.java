package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Channel;
import com.dconnect.infrastructure.domain.ConnectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionsChannelsRepository extends JpaRepository<ConnectionDetails, Long> {

    boolean existsByChannelAndActiveIsTrue(Channel channel);
}
