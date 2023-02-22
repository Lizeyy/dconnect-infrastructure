package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.ConnectionJoinRequest;
import com.dconnect.client.protocol.domain.response.ConnectionJoinResponse;
import com.dconnect.infrastructure.client.DiscordClient;
import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.domain.Invitation;
import com.dconnect.infrastructure.error.ChannelAlreadyUsed;
import com.dconnect.infrastructure.error.InvitationAlreadyExist;
import com.dconnect.infrastructure.error.TokenNotActive;
import com.dconnect.infrastructure.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final TokenService tokenService;
    private final ChannelService channelService;
    private final DiscordClient discordClient;

    public ConnectionJoinResponse createInvitation(ConnectionJoinRequest request) {
        if (!tokenService.checkIfTokenExist(request.getToken())) {
            throw new TokenNotActive("Podany token nie pasuje do żadnego połączenia lub jest nieaktywny!");
        }

        if (channelService.checkIfChannelExist(request.getServerId(), request.getChannelId())) {
            throw new ChannelAlreadyUsed("Kanał już jest połączony z siecią!");
        }

        if (checkIfInvitationExist(request.getServerId(), request.getChannelId())) {
            throw new InvitationAlreadyExist("Dla tego kanału juz istnieje niezaakceptowane zaproszenie!");
        }

        final Connection connection = tokenService.getConnectionByToken(request.getToken());

        final Invitation invitation = Invitation.builder()
                .connectionId(connection.getId())
                .serverId(request.getServerId())
                .serverName(discordClient.getServerInfo(request.getServerId()).getName())
                .channelId(request.getChannelId())
                .createdBy(request.getCreationBy())
                .creationDate(LocalDateTime.now())
                .build();

        invitationRepository.save(invitation);

        return new ConnectionJoinResponse(connection.getName());
    }

    private boolean checkIfInvitationExist(String serverId, String channelId) {
        return invitationRepository.findByServerIdAndChannelId(serverId, channelId).isPresent();
    }


}
