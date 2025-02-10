package app.dto.user;

import java.util.UUID;

import app.entity.User;

public class UserInfoDTO {
    private UUID id;
    private String fullname;
    private String email;

    // Constructor, Getter & Setter
    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.fullname = user.getfullname();
        this.email=user.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
