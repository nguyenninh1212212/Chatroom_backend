package app.dto.message;

import java.time.LocalDateTime;
import app.dto.user.UserInfoDTO;
import app.entity.User;

public class MessagesDTO {

    private String content;
    private UserInfoDTO user;
    private LocalDateTime created;
    private boolean isMine;

    // Constructor
    public MessagesDTO(String content, User user, LocalDateTime created, Boolean isMine) {
        this.content = content;
        this.user = new UserInfoDTO(user);
        this.created = created;
        this.isMine = isMine;
    }

    // Constructor mặc định
    public MessagesDTO() {
    }

    // Getters và Setters
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

//    // Builder Pattern
//    public static class Builder {
//        private String content;
//        private User user;
//        private LocalDateTime created;
//        private boolean isMine;
//
//        public Builder setContent(String content) {
//            this.content = content;
//            return this;
//        }
//
//        public Builder setUser(User user) {
//            this.user = user;
//            return this;
//        }
//
//        public Builder setCreated(LocalDateTime created) {
//            this.created = created;
//            return this;
//        }
//
//        public Builder setIsMine(boolean isMine) {
//            this.isMine = isMine;
//            return this;
//        }
//
//        public MessagesDTO build() {
//            return new MessagesDTO(content, user , created, isMine);
//        }
//    }
}
