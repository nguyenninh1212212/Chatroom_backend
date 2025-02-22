package app.dto.room;

import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.Room;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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

}
