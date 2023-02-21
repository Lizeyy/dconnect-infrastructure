package com.dconnect.infrastructure.endpoint;

import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.request.ConnectionJoinRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.client.protocol.domain.response.ConnectionJoinResponse;
import com.dconnect.infrastructure.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping({"/api/connection"})
    public ConnectionCreateResponse createConnection(@Valid @RequestBody ConnectionCreateRequest request) {
        return connectionService.createConnection(request);
    }

    @PutMapping({"/api/connection/join"})
    public ConnectionJoinResponse joinConnection(@Valid @RequestBody ConnectionJoinRequest request) {
        return connectionService.joinConnection(request);
    }
}
