package org.example.hotelmanagementpractice.repositories;

import org.example.hotelmanagementpractice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> getRoomByRoomNumber(int roomNumber);

    Optional<List<Room>> getRoomsByHotelId(Long hotel_id);

    boolean existsByRoomNumber(int roomNumber);
}
