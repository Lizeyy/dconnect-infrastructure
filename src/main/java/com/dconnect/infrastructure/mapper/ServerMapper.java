package com.dconnect.infrastructure.mapper;

import com.dconnect.client.protocol.domain.request.ServerCreateRequest;
import com.dconnect.infrastructure.domain.Server;

import java.time.OffsetDateTime;


public class ServerMapper {

    public static Server map(ServerCreateRequest request) {
        final Server server = new Server();
        server.setDiscordServerId(request.getServerId());
        //server.setName();
        server.setCreationBy(request.getCreationBy());
        server.setCreationDate(OffsetDateTime.now());
        return server;
    }
}
