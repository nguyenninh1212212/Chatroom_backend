package app.dto.room;

import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.Room;

public class RoomInfoDTO {
	private UUID id;
	private String name;
	private UserInfoDTO owner;
	private String latestMessage;
	
	public RoomInfoDTO(Room room,String latestMessage) {
		this.id = room.getId();
		this.name = room.getName();
		this.setOwner(new UserInfoDTO(room.getOwner()));
		this.latestMessage=latestMessage;
	}
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserInfoDTO getOwner() {
		return owner;
	}
	public void setOwner(UserInfoDTO owner) {
		this.owner = owner;
	}


	public String getLatestMessage() {
		return latestMessage;
	}


	public void setLatestMessage(String latestMessage) {
		this.latestMessage = latestMessage;
	}
	
	
	
	
	
}
