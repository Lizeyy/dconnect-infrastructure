package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.ServerCreateRequest;
import com.dconnect.infrastructure.domain.Server;
import com.dconnect.infrastructure.mapper.ServerMapper;
import com.dconnect.infrastructure.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;

    public Server createServer(ServerCreateRequest request) {
        Server server = getServerById(request.getServerId());

        if (Objects.isNull(server)) {
            server = ServerMapper.map(request);
            return serverRepository.save(server);
        }
        return server;
    }

    public Server getServerById(String id) {
        return serverRepository.findAllByDiscordServerId(id).stream().findFirst().orElse(null);
    }



}
