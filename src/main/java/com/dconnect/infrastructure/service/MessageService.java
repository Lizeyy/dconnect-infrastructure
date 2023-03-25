package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.MessageRequest;
import com.dconnect.client.protocol.domain.response.MessageSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final RabbitTemplate rabbitTemplate;
    private final ServerService serverService;
    private final ConnectionService connectionService;

    public void sendMessage(MessageRequest request) {
        Assert.isTrue(checkIfChannelExistInConnection(request.getChannelId()), "Nie znaleziono aktywnego połączenia z kanałem");

        final MessageSend message = createMessageSend(request);
        rabbitTemplate.convertAndSend("messageExchange", "messageRoutingKey",
                rabbitTemplate.getMessageConverter().toMessage(message, new MessageProperties()));
    }

    private MessageSend createMessageSend(MessageRequest messageRequest) {
        return MessageSend.builder()
                .channelsId(connectionService.getChannelsInConnectionByChannelId(messageRequest.getChannelId()))
                .userId(messageRequest.getUser())
                .channelRoot(messageRequest.getChannelId())
                .serverRoot(serverService.getServerByChannel(messageRequest.getChannelId()).getName())
                .message(messageRequest.getMessage())
                .build();
    }

    private boolean checkIfChannelExistInConnection(String channelId) {
        return connectionService.checkIfChannelExistInConnection(channelId);
    }
}
