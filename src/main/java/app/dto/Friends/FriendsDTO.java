package app.dto.Friends;


import app.dto.user.UserInfoDTO;
import app.entity.User;
import app.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@Builder
public class FriendsDTO {
    private UUID id;
    private UserInfoDTO user;
    private LocalDateTime created;
    private FriendStatus status;
    }
