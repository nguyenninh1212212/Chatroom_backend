package app.repository;

import app.entity.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface MessageReponsitory extends JpaRepository<Message, UUID> {
	List<Message> findAllByRoomId (UUID room_id);
	Message findByRoomIdAndId(UUID room_id,UUID id);
	Page<Message> findByRoomIdAndCreatedBeforeOrderByCreatedDesc(UUID roomId, LocalDateTime lastCreatedTime, Pageable pageable);
	@Query("SELECT m FROM Message m WHERE m.room.id = :room_id ORDER BY m.created DESC LIMIT 1")
	Optional<Message> findLatestMessageByRoomId(@Param("room_id") UUID room_id);





}
