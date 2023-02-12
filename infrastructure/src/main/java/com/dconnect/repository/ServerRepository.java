package com.dconnect.infrastructureservice.repository;

import com.dconnect.infrastructureservice.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server, Long> {

    List<Server> findAllByDiscordServerId(String discordServerId);
}
