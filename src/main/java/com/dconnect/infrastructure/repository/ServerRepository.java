package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {

    Optional<Server> findByDiscordServerId(String discordServerId);


}
