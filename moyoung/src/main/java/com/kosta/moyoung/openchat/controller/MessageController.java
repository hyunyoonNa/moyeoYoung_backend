package com.kosta.moyoung.openchat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.kosta.moyoung.openchat.dto.Message;

@CrossOrigin(origins = "*")
@Controller
public class MessageController {
    @MessageMapping("/chat/{room}/sendMessage")
	@SendTo("/topic/{room}")
	public Message sendMessage(@DestinationVariable String room, @Payload Message chatMessage) {
	    return chatMessage;
	}
}