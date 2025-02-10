package app.dto.message;

import java.util.UUID;

import app.entity.Message;

public class MesssageLatestDTO {
	private UUID id;
	private UUID room_id;
	private String content;
	
	public MesssageLatestDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.room_id = message.getRoom().getId();
    }
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UUID getRoom_id() {
		return room_id;
	}
	public void setRoom_id(UUID room_id) {
		this.room_id = room_id;
	}
	
	
	

}
