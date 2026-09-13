package org.mlagdevelopment.messenger.thelostone.server.service;


import org.mlagdevelopment.messenger.thelostone.server.domain.Room;
import org.mlagdevelopment.messenger.thelostone.server.domain.RoomMember;
import org.mlagdevelopment.messenger.thelostone.server.domain.RoomType;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.CreateRoomRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.RoomResponse;
import org.mlagdevelopment.messenger.thelostone.server.repository.RoomMemberRepository;
import org.mlagdevelopment.messenger.thelostone.server.repository.RoomRepository;
import org.mlagdevelopment.messenger.thelostone.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {


    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository, RoomMemberRepository roomMemberRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public RoomResponse createRoom(Long ownerId, CreateRoomRequest request) {
        if (!userRepository.existsById(ownerId)) {
            throw new IllegalArgumentException("User not found");
        }

        Room room = new Room();
        room.setName(request.name());
        room.setType(RoomType.GROUP);
        room.setOwnerId(ownerId);
        Room saved = roomRepository.save(room);

        RoomMember member = new RoomMember();
        member.setRoomId(saved.getId());
        member.setUserId(ownerId);
        roomMemberRepository.save(member);

        return toResponse(saved);

    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getUserRooms(Long userId){
        return roomMemberRepository.findAllByUserId(userId)
                .stream()
                .map(roomMember -> roomRepository.findById(roomMember.getRoomId()).orElseThrow())
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse joinRoom(Long roomId,Long userId){
        Room room = roomRepository.findById(roomId).orElseThrow();
        if(!userRepository.existsById(userId)){
            throw new IllegalArgumentException("User not found");
        }
        if(roomMemberRepository.existsByRoomIdAndUserId(roomId,userId)){
            throw new IllegalArgumentException("Already a member of this room");
        }

        RoomMember member = new RoomMember();
        member.setRoomId(roomId);
        member.setUserId(userId);
        roomMemberRepository.save(member);
        return toResponse(room);
    }

    @Transactional
    public void leaveRoom(Long roomId,Long userId){
        RoomMember  roomMember = roomMemberRepository.findByRoomIdAndUserId(roomId,userId).orElseThrow(()->new IllegalArgumentException("Not a member of this room"));
        roomMemberRepository.delete(roomMember);
    }

    @Transactional
    public RoomResponse getRoom(Long roomId){
        Room room = roomRepository.findById(roomId).orElseThrow(()->new IllegalArgumentException("Room not found"));
        return toResponse(room);
    }


    private RoomResponse toResponse(Room room){
        long memberCount = roomMemberRepository.countByRoomId(room.getId());
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getType().name(),
                room.getOwnerId(),
                room.getCreatedAt(),
                memberCount
        );
    }
}
