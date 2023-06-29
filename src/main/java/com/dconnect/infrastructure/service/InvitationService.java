package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.notification.NewInvitation;
import com.dconnect.client.protocol.domain.request.ConnectionJoinRequest;
import com.dconnect.client.protocol.domain.request.InvitationRequest;
import com.dconnect.client.protocol.domain.response.InvitationResponse;
import com.dconnect.client.protocol.domain.response.ConnectionJoinResponse;
import com.dconnect.client.protocol.domain.response.ServerInfo;
import com.dconnect.infrastructure.client.DiscordClient;
import com.dconnect.infrastructure.domain.Channel;
import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.domain.Invitation;
import com.dconnect.infrastructure.domain.Server;
import com.dconnect.infrastructure.error.ChannelAlreadyUsed;
import com.dconnect.infrastructure.error.InvitationAlreadyExist;
import com.dconnect.infrastructure.error.InvitationNotFoundException;
import com.dconnect.infrastructure.error.TokenNotActive;
import com.dconnect.infrastructure.repository.InvitationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final TokenService tokenService;
    private final ChannelService channelService;
    private final ServerService serverService;
    private final ConnectionService connectionService;
    private final DiscordClient discordClient;

    @Transactional
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
        final Server server = serverService.getOrCreateServer(request.getServerId(), request.getCreationBy());
        final Channel channel = channelService.getChannelOrCreate(request.getChannelId(), server, request.getCreationBy());
        serverService.addChannelToServer(server, channel);

        final Invitation invitation = Invitation.builder()
                .connectionId(connection.getId())
                .serverId(request.getServerId())
                .serverName(discordClient.getServerInfo(request.getServerId()).getName())
                .channelId(request.getChannelId())
                .createdBy(request.getCreationBy())
                .creationDate(LocalDateTime.now())
                .build();

        invitationRepository.save(invitation);
        sendNotification(prepareNewInvitationNotification(invitation, connection.getRootChannel().getServer().getDiscordServerId()));

        return new ConnectionJoinResponse(connection.getName());
    }

    @Transactional
    public InvitationResponse addInvitation(InvitationRequest request) {
        final Invitation invitation = getInvitation(request.getInvitationId());

        if (channelService.checkIfChannelExist(invitation.getServerId(), invitation.getChannelId())) {
            throw new ChannelAlreadyUsed("Kanał już jest połączony z siecią!");
        }

        final Channel channel = channelService.getChannel(invitation.getChannelId());
        final Connection connection = connectionService.addToConnection(invitation.getConnectionId(), channel, invitation.getCreatedBy());
        removeInvitation(request);

        return InvitationResponse.builder()
                .connectionName(connection.getName())
                .serverName(channel.getServer().getName())
                .build();
    }

    public InvitationResponse removeInvitation(InvitationRequest request) {
        final Invitation invitation = getInvitation(request.getInvitationId());
        final String connectionName = connectionService.getConnectionName(invitation.getConnectionId());
        invitationRepository.delete(invitation);
        return InvitationResponse.builder()
                .connectionName(connectionName)
                .serverName(invitation.getServerName())
                .build();
    }

    private boolean checkIfInvitationExist(String serverId, String channelId) {
        return invitationRepository.findByServerIdAndChannelId(serverId, channelId).isPresent();
    }

    private Invitation getInvitation(String invitationId) {
        return invitationRepository.findById(invitationId).orElseThrow(() -> new InvitationNotFoundException("Nie znaleziono zaproszenia"));
    }

    private NewInvitation prepareNewInvitationNotification(Invitation invitation, String serverRootId) {
        return NewInvitation.builder()
                .connectionId(invitation.getConnectionId())
                .connectionName(connectionService.getConnectionName(invitation.getConnectionId()))
                .serverId(invitation.getServerId())
                .iconUrl(getServerInfo(invitation.getServerId()).getIcon())
                .serverName(invitation.getServerName())
                .invitationId(invitation.getId())
                .createdBy(invitation.getCreatedBy())
                .createdByName(getUsername(invitation.getCreatedBy()))
                .serverConnectionOwnerId(getServerInfo(serverRootId).getOwner_id())
                .creationDate(invitation.getCreationDate())
                .build();
    }

    private ServerInfo getServerInfo(String serverRootId) {
        return discordClient.getServerInfo(serverRootId);
    }

    private String getUsername(String userId) {
        return discordClient.getUserInfo(userId).getUsername();
    }

    private void sendNotification(NewInvitation invitation) {
        discordClient.sendPrivateNotification(invitation);
    }

}
