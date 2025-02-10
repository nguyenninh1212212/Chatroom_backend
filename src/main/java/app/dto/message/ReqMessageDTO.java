package app.dto.message;

import java.time.LocalDateTime;
import java.util.UUID;

import app.dto.room.RoomInfoDTO;
import app.dto.user.UserInfoDTO;
import app.entity.Room;
import app.entity.User;

public class ReqMessageDTO {
	private UUID id;

    private String content;

    private UserInfoDTO user;

    private RoomInfoDTO room;
    
    private LocalDateTime created;
    private String updated;
    private String deleted;
    
    public ReqMessageDTO(UUID id,String content,User user,Room room) {
    	this.id=id;                  
    	this.content=content;
    	this.user=new UserInfoDTO(user);
    	this.room=new RoomInfoDTO(room,content);
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
	public UserInfoDTO getUser() {
		return user;
	}
	public void setUser(UserInfoDTO user) {
		this.user = user;
	}
	public RoomInfoDTO getRoom() {
		return room;
	}
	public void setRoom(RoomInfoDTO room) {
		this.room = room;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
    
    
}
