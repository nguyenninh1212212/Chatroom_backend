package app.dto.user;

import app.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;



@Getter @Setter
public class ReqUserDTO {
	    private UUID id;
	    private String username;
	    private String fullname;
	    private String email;
	    private LocalDateTime created;
	    private String updated;
	    private String deleted;
	    
	    public ReqUserDTO(User user) {
	        this.id = user.getId();
	        this.username = user.getUsername();
	        this.fullname=user.getFullname();
	        this.email = user.getEmail();
	        this.created=user.getCreated();
	    }
	    

}
