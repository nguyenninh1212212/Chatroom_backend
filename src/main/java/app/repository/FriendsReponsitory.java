package app.repository;


import app.entity.Friends;
import app.entity.User;
import app.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendsReponsitory extends JpaRepository<Friends, UUID> {
    @Query("SELECT f FROM Friends f WHERE f.user1 = :user OR f.user2 = :user ")
    List<Friends> findAllByUser(@Param("user") User user);

}
