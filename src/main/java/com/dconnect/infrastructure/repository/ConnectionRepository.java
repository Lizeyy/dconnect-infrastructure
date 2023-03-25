package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    List<Connection> findAllByChannelsServer(Server server);

    Connection findByChannelsDiscordChannelId(String discordChannelId);

    boolean existsByChannelsDiscordChannelId(String discordChannelId);

}
