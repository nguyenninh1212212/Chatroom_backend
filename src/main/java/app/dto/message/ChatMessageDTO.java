package app.dto.message;

import java.util.UUID;



public class ChatMessageDTO {
    private UUID user_id;
    private String content;
    
	public ChatMessageDTO(UUID user_id, String content) {
		this.user_id = user_id;
		this.content = content;
	}
	public UUID getUser_id() {
		return user_id;
	}
	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
    
}