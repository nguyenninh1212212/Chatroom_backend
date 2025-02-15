package app.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

import app.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfoDTO {
    private String fullname;
    private String email;
    private LocalDateTime created;
    private String updated;
    // Constructor, Getter & Setter
    public UserInfoDTO(User user) {
        this.fullname = user.getFullname();
        this.email=user.getEmail();
        this.created=user.getCreated();
        this.updated=user.getUpdated().toString();
    }


}
