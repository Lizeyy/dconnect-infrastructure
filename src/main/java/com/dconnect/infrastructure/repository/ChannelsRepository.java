package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelsRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByServerDiscordServerIdAndDiscordChannelIdAndDetailsActiveIsTrue(String serverId, String discordChannelId);

    Optional<Channel> findByDiscordChannelId(String discordChannelId);

}
