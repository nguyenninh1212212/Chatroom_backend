package app.dto.Friends;

import lombok.*;

import java.util.UUID;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FriendRespondDTO {
    private UUID id;
    private Boolean appect;
}
