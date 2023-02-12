package com.dconnect.infrastructureservice.repository;

import com.dconnect.infrastructureservice.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelsRepository extends JpaRepository<Channel, Long> {
}
