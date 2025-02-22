package app.repository;

import app.entity.Room;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SearchReponsitory extends JpaRepository<Room, UUID> {
    @Query("SELECT r FROM Room r JOIN Member m ON m.room.id = r.id WHERE m.user.id = :user_id AND r.name LIKE %:keyword%")
    List<Room> searchRooms(@Param("user_id") UUID user_id, @Param("keyword") String keyword);

    @Query("SELECT u FROM User u WHERE  (u.fullname LIKE %:keyword% OR u.email LIKE %:keyword%)")
    List<User> searchUser( @Param("keyword") String keyword);

}
