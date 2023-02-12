package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server, Long> {

    List<Server> findAllByDiscordServerId(String discordServerId);
}
