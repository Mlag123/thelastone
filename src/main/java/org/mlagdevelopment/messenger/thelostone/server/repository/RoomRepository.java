package org.mlagdevelopment.messenger.thelostone.server.repository;

import org.mlagdevelopment.messenger.thelostone.server.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository  extends JpaRepository<Room,Long> {

    List<Room> findAllByOwnerId(Long ownerId);
    boolean existsByName(String name);

}
