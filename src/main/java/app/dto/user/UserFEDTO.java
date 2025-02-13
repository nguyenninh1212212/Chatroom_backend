package app.dto.user;

import app.entity.User;

public class UserFEDTO {
    private String fullName;
    private String email;

    public UserFEDTO(User user){
        this.fullName=user.getfullname();
        this.email=user.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
