package com.dconnect.infrastructure.endpoint;

import com.dconnect.client.protocol.domain.request.MessageRequest;
import com.dconnect.infrastructure.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("api/message")
    public void getMessage(@Valid @RequestBody MessageRequest request) {
        messageService.sendMessage(request);
    }

}
