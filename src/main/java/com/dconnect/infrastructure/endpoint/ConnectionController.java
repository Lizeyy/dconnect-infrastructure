package com.dconnect.infrastructure.endpoint;

import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.request.ConnectionJoinRequest;
import com.dconnect.client.protocol.domain.request.ConnectionQuitRequest;
import com.dconnect.client.protocol.domain.request.InvitationRequest;
import com.dconnect.client.protocol.domain.response.*;
import com.dconnect.infrastructure.service.ConnectionService;
import com.dconnect.infrastructure.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;
    private final InvitationService invitationService;

    @PostMapping("/api/connection")
    public ConnectionCreateResponse createConnection(@Valid @RequestBody ConnectionCreateRequest request) {
        return connectionService.createConnection(request);
    }

    @PutMapping("/api/connection/join")
    public ConnectionJoinResponse joinConnection(@Valid @RequestBody ConnectionJoinRequest request) {
        return invitationService.createInvitation(request);
    }

    @PostMapping("api/connection/add")
    public InvitationResponse addConnection(@Valid @RequestBody InvitationRequest request) {
        return invitationService.addInvitation(request);
    }

    @PostMapping("api/connection/quit")
    public ConnectionQuitResponse addConnection(@Valid @RequestBody ConnectionQuitRequest request) {
        return connectionService.quitConnection(request);
    }

    @PostMapping("api/connection/invitation-delete")
    public InvitationResponse removeInvitation(@Valid @RequestBody InvitationRequest request) {
        return invitationService.removeInvitation(request);
    }

    @GetMapping("api/connection/{serverId}")
    public ConnectionListOnServerResponse getConnectionListOnServerResponse(@PathVariable("serverId") String serverId) {
        return connectionService.getConnectionListOnServerResponse(serverId);
    }

    @GetMapping("api/connection/servers/{channelId}")
    public ConnectionServersListResponse getConnectionServersListResponse(@PathVariable("channelId") String channelId) {
        return connectionService.getConnectionServersListResponse(channelId);
    }

}
