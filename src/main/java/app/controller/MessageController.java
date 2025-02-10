package app.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.dto.ErrorResponse.ErrorResponseDTO;
import app.dto.message.ChatMessageDTO;
import app.dto.message.MessagesDTO;
import app.dto.message.MesssageLatestDTO;
import app.dto.message.ReqMessageDTO;
import app.service.MessageService;

@RequestMapping("/message")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageSer;

    @PostMapping("/chat")
    public ResponseEntity<?> create(@RequestBody(required = true) Map<String, String> req) {
        try {
            UUID userId = UUID.fromString(req.get("user_id"));
            UUID roomId = UUID.fromString(req.get("room_id"));
            String content = req.get("content");
            ChatMessageDTO messageSend=new ChatMessageDTO(userId,content);
            ReqMessageDTO message = messageSer.create(messageSend, roomId);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Successfully !!🎈", "data", message));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> messages(@RequestParam(required = true) UUID user_id ,@RequestParam(required = true) UUID room_id) {
        try {
        	List<MessagesDTO> messages = messageSer.messages(room_id, user_id)
        		    .stream()
        		    .sorted(Comparator.comparing(MessagesDTO::getCreated).reversed())
        		    .collect(Collectors.toList());
            return ResponseEntity.ok(Map.of("message", "Successfully !!🎈", "data", messages));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format");
        } catch (Exception e) {
        	ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
                );
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<Optional<MesssageLatestDTO>> getLatestMessages(@RequestParam(required = true) UUID room_id) {
        Optional<MesssageLatestDTO> messages = messageSer.getLatestMessages(room_id);
        return ResponseEntity.ok(messages);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> removeMessages(@RequestBody(required = true) Map<String,UUID> req) {
        try {
        	UUID message_id=req.get("id");
            UUID room_id=req.get("room_id");
            messageSer.removeMessage(room_id, message_id);
            return ResponseEntity.ok(Map.of("message","successfully"));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
    }
}
