package app.dto.message;

import app.entity.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessagesDTO {
    private String content;
    private LocalDateTime created;
    private boolean isMine;

    public MessagesDTO(Message message, UUID currentUserId) {
        this.content = message.getContent();
        this.created=message.getCreated();
        this.isMine = message.getUser().getId().equals(currentUserId);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime time) {
        this.created = time;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
