package app.dto.Friends;

import app.dto.user.UserInfoDTO;
import app.enums.FriendStatus;
import lombok.*;

import java.util.UUID;


@Data
@AllArgsConstructor
@Getter @Setter
@Builder
public class ReqFriendsDTO {
    private UUID id;
    private UserInfoDTO sender;
    private UserInfoDTO receiver;
    private FriendStatus status;
    private Boolean isMe;
}
