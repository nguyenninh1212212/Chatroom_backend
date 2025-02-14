package app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.Room;
@Repository
public interface  RoomReponsitory extends JpaRepository<Room,UUID> {
	Optional<Room> findByIdAndName (UUID id,String name);
	@Query("SELECT r FROM Room r " +
		       "JOIN r.members m " +
		       "JOIN m.user u " +
		       "WHERE u.id = :userId")
	List<Room> findAllByUserId(@Param("userId") UUID userId);

	@Query("SELECT DISTINCT r FROM Room r " +
			"JOIN User u ON u.id = r.owner.id " +
			"WHERE r.name LIKE %:keyword% " +
			"OR u.fullname LIKE %:keyword% " +
			"OR u.email LIKE %:keyword%")
	List<Room> searchRooms(@Param("keyword") String keyword);

	Optional<Room> findById(UUID id);

}
