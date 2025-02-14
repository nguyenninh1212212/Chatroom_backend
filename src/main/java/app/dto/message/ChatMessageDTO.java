package app.dto.message;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessageDTO {
	private UUID user_id;
	private UUID room_id;  // Thêm room_id vào DTO
	private String content;
	private LocalDateTime created;

	public ChatMessageDTO(UUID user_id, UUID room_id, String content) {
		this.user_id = user_id;
		this.room_id = room_id;
		this.content = content;
		this.created=LocalDateTime.now();
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created_at) {
		this.created = created_at;
	}

	public UUID getUser_id() {
		return user_id;
	}

	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}

	public UUID getRoom_id() {
		return room_id;
	}

	public void setRoom_id(UUID room_id) {
		this.room_id = room_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	@Override
	public String toString() {
		return "ChatMessageDTO{" +
				"user_id=" + user_id +
				", room_id=" + room_id +
				", content='" + content + '\'' +
				'}';
	}
}
