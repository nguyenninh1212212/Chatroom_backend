package app.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter @Setter
public class ChatMessageDTO {
	private String email;
	private UUID room_id;  // Thêm room_id vào DTO
	private String content;
	private LocalDateTime created;
	private String sender;


	public ChatMessageDTO(String email, UUID room_id, String content,String sender) {
		this.email = email;
		this.room_id = room_id;
		this.content = content;
		this.created=LocalDateTime.now();
		this.sender=sender;
	}


}
