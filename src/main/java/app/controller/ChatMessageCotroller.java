package app.controller;

import java.util.List;
import java.util.UUID;

import app.dto.room.RoomInfoDTO;
import app.entity.Message;
import app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.dto.message.ChatMessageDTO;
import app.dto.message.ReqMessageDTO;
import app.service.MessageService;

@Controller
public class ChatMessageCotroller {
	private final SimpMessagingTemplate messagingTemplate;
@Autowired
private MessageService messageSer;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RoomService roomService;

    public ChatMessageCotroller(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room/{room_id}")
    public void sendMessage(@DestinationVariable String room_id, @Payload ChatMessageDTO chatMessageDTO) {
        if (room_id == null || room_id.isEmpty()) {
            throw new IllegalArgumentException("roomId must not be null or empty");
        }

        try {
            UUID roomUUID = UUID.fromString(room_id);
            messageService.create(chatMessageDTO, roomUUID);

            // 1. Gửi tin nhắn đến phòng cụ thể
            messagingTemplate.convertAndSend("/topic/room/" + room_id, chatMessageDTO);

            // 2. Lưu tin nhắn vào database


        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID format: " + room_id);
            throw new IllegalArgumentException("Invalid room ID format", e);
        }
    }

}
