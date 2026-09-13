package org.mlagdevelopment.messenger.thelostone.server.repository;

import org.mlagdevelopment.messenger.thelostone.server.domain.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    boolean existsByRoomIdAndUserId(Long roomId, Long userId);

    Optional<RoomMember> findByRoomIdAndUserId(Long roomId, Long userId);

    List<RoomMember> findAllByUserId(Long userId);

    List<RoomMember> findAllByRoomId(Long roomId);

    long countByRoomId(Long roomId);
}
