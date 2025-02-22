package app.service;

import app.dto.room.RoomInfoDTO;
import app.dto.search.SearchDTO;
import app.dto.user.UserInfoDTO;
import app.repository.SearchReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private SearchReponsitory searchRepository;

    public SearchDTO searchRoomsAndUsers(UUID user_id, String keyword) {
        List<RoomInfoDTO> rooms = searchRepository.searchRooms(user_id, keyword).stream().map(room-> new RoomInfoDTO(room,"")).collect(Collectors.toList());
        List<UserInfoDTO> users = searchRepository.searchUser(keyword).stream().map(user-> new UserInfoDTO(user)).limit(10).collect(Collectors.toList());
        SearchDTO result = SearchDTO.builder().user(users).room(rooms).build();
        return result;
    }
}
