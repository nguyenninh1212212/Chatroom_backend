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
            // Validate UUID format
            UUID roomUUID;
            try {
                roomUUID = UUID.fromString(room_id);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid UUID format: " + room_id);
                throw new IllegalArgumentException("Invalid room ID format", e);
            }

            // Send message to topic
            messagingTemplate.convertAndSend("/topic/room/" + room_id, chatMessageDTO);

            // Call message service to save the chat message
            messageService.create(chatMessageDTO, roomUUID);

        } catch (IllegalArgumentException e) {
            // Additional logging or rethrowing might be necessary depending on the context
            System.err.println("Error processing room ID or message: " + e.getMessage());
            throw e;  // Re-throw the exception for proper handling upstream
        }
    }

}
