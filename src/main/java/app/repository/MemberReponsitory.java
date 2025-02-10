package app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Member;
import app.entity.Room;
import app.entity.User;

@Repository
public interface MemberReponsitory extends JpaRepository<Member, UUID> {
	List<Member> findAllByRoom(Room room);
	Optional<Member> findByUserIdAndRoomId(UUID user_id,UUID room_id);
	Optional<Member> findByUserId(UUID user_id);
	Member findByUserAndRoom(User user, Room room);
}
