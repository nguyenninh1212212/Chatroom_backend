package app.controller;

import java.util.UUID;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.dto.message.ChatMessageDTO;
import app.dto.message.ReqMessageDTO;
import app.service.MessageService;

@Controller
public class ChatMessageCotroller {
	private final SimpMessagingTemplate simpMes ;	
	private final MessageService messSer;
	public ChatMessageCotroller(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.simpMes = messagingTemplate;
        this.messSer = messageService;
    }
	
	 @MessageMapping("/room/{roomId}")
	    public void sendMessage(@DestinationVariable UUID room_id, ChatMessageDTO message) {
	        ReqMessageDTO savedMessage = messSer.create(message, room_id);
	        simpMes.convertAndSend("/room/" + room_id, savedMessage);
	    }
	 	
	
}
