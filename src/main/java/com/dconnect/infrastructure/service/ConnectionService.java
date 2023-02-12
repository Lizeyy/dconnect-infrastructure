package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.mapper.ConnectionMapper;
import com.dconnect.infrastructure.repository.ConnectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    public ConnectionCreateResponse createConnection(ConnectionCreateRequest request) {
        final Connection connection = ConnectionMapper.INSTANCE.map(request);
        return new ConnectionCreateResponse(); // ConnectionMapper.INSTANCE.map(connection);
    }

    private boolean checkServerExist(String serverId) {
        return true;
    }
}
