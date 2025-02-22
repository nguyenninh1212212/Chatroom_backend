package app.dto.room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.Member;
import app.entity.Message;
import app.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReqRoomDTO {
	 	private UUID id;
	    private String name;
	    private LocalDateTime created;
	    private String updated;
	    private String deleted;
	    private UserInfoDTO is_owner;
	    
	    public ReqRoomDTO(UUID id,String name,LocalDateTime created,User is_owner) {
	    	this.id=id;
	    	this.name=name;
	    	this.created=created;
	    	this.setIs_owner(new UserInfoDTO(is_owner));
	    }
	    

	    
}
