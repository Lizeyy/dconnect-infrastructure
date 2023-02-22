package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InvitationRepository extends MongoRepository<Invitation, String> {

    Optional<Invitation> findByServerIdAndChannelId(String serverId, String channelId);
}
