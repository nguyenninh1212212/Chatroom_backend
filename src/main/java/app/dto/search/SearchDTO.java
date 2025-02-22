package app.dto.search;

import app.dto.room.RoomInfoDTO;
import app.dto.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
@AllArgsConstructor
@Builder
public class SearchDTO {
    private List<UserInfoDTO> user;
    private List<RoomInfoDTO> room;
}
