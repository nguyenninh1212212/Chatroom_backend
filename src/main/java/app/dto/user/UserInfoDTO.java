package app.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

import app.entity.User;

public class UserInfoDTO {
    private UUID id;
    private String fullname;
    private String email;
    private LocalDateTime created;
    private String updated;
    // Constructor, Getter & Setter
    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.fullname = user.getfullname();
        this.email=user.getEmail();
        this.created=user.getCreated();
        this.updated=user.getUpdated();
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
}
