package org.mlagdevelopment.messenger.thelostone.server.repository;

import org.mlagdevelopment.messenger.thelostone.server.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findTop50ByRoomIdOrderByCreatedAtDesc(Long id);
}
