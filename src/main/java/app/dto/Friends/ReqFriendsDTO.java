package app.dto.Friends;

import app.dto.user.UserInfoDTO;
import app.enums.FriendStatus;
import lombok.*;


@Data
@AllArgsConstructor
@Getter @Setter
@Builder
public class ReqFriendsDTO {
    private UserInfoDTO sender;
    private UserInfoDTO receiver;
    private FriendStatus status;
    private Boolean isMe;
}
