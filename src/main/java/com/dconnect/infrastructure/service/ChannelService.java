package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.response.ChannelInfo;
import com.dconnect.infrastructure.client.DiscordClient;
import com.dconnect.infrastructure.domain.Channel;
import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.domain.Server;
import com.dconnect.infrastructure.error.ChannelNotFoundException;
import com.dconnect.infrastructure.repository.ChannelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelsRepository channelsRepository;
    private final DiscordClient discordClient;

    public Channel createChannel(Server server, String channelId, String creationBy) {
        final ChannelInfo channelInfo = discordClient.getChannelInfo(channelId);

        final Channel channel = new Channel();
        channel.setServer(server);
        channel.setDiscordChannelId(channelId);
        channel.setName(channelInfo.getName());
        channel.setCreationBy(creationBy);
        channel.setCreationDate(OffsetDateTime.now());

        return channelsRepository.save(channel);
    }

    public Channel getChannelOrCreate(String channelDiscordId, Server server, String createdBy) {
        return channelsRepository.findByDiscordChannelId(channelDiscordId)
                .orElseGet( () -> createChannel(server, channelDiscordId, createdBy));
    }

    public Channel getChannel(String channelDiscordId) {
        return channelsRepository.findByDiscordChannelId(channelDiscordId).orElseThrow(() -> new ChannelNotFoundException("Nie znaleziono kanału"));
    }

    public void addChannelToConnection(Channel channel, Connection connection) {
        channel.setConnection(connection);
        channelsRepository.save(channel);
        connection.getChannels().add(channel);
    }

    public boolean checkIfChannelExist(String serverId, String channelId) {
        return channelsRepository.findByServerDiscordServerIdAndDiscordChannelIdAndDetailsActiveIsTrue(serverId, channelId).isPresent();
    }

}
