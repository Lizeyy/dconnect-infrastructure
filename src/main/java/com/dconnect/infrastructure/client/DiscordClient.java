package com.dconnect.infrastructure.client;

import com.dconnect.client.DiscordRestClient;
import com.dconnect.client.protocol.domain.response.ChannelInfo;
import com.dconnect.client.protocol.domain.response.ServerInfo;
import com.dconnect.infrastructure.error.ErrorAtGettingInfoFromDiscord;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscordClient {

    private final DiscordRestClient discordRestClient;

    public ServerInfo getServerInfo(String serverId) {
        try {
            return discordRestClient.getServerInfo(serverId);
        } catch (FeignException.FeignClientException e) {
            throw new ErrorAtGettingInfoFromDiscord("Nie udało się pobrać informacji o serwerze!");
        }
    }

    public ChannelInfo getChannelInfo(String channelId) {
        try {
            return discordRestClient.getChannelInfo(channelId);
        } catch (FeignException.FeignClientException e) {
            throw new ErrorAtGettingInfoFromDiscord("Nie udało się pobrać informacji o kanale!");
        }
    }

}
