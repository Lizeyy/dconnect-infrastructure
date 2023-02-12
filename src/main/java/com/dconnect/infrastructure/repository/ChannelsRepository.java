package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelsRepository extends JpaRepository<Channel, Long> {
}
