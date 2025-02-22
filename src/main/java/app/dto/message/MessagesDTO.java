package app.dto.message;

import app.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter @Setter
public class MessagesDTO {


    private String content;
    private LocalDateTime created;
    private boolean isMine;
    private String sender;
    private String email;

    public MessagesDTO(Message message, UUID currentUserId ) {
        this.content = message.getContent();
        this.created=message.getCreated();
        this.isMine = message.getUser().getId().equals(currentUserId);
        this.sender = message.getUser().getFullname();
        this.email = message.getUser().getEmail();
    }
}
