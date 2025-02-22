package app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.Room;
@Repository
public interface  RoomReponsitory extends JpaRepository<Room,UUID> {
	Optional<Room> findByIdAndName (UUID id,String name);
	@Query("SELECT r FROM Room r " +
			"JOIN FETCH r.members m " +
			"JOIN FETCH m.user u " +
			"WHERE u.id = :userId")
	Page<Room> findAllByUserId(UUID userId, Pageable pageable);
	@Query("SELECT r FROM Room r JOIN Member m ON m.room.id = r.id WHERE m.room.id = :roomId AND m.user.id = :userId")
	Optional<Room> findByIdAndUserId(UUID roomId,UUID userId);

}
