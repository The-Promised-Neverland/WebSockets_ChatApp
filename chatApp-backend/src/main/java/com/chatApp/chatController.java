package com.chatApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@Slf4j
public class chatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message chatMessage){
        return chatMessage;
    }

    @MessageMapping("/private-message")
    private Message receivePrivateMessage(@Payload Message chatMessage){
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverName(), "/private", chatMessage); // dynamically sends to the user ex,/user/david/private
        log.info(chatMessage.toString());
        return chatMessage;
    }
}
