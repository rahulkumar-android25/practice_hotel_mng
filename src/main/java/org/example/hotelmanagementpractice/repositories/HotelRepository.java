package org.example.hotelmanagementpractice.repositories;

import org.example.hotelmanagementpractice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {


    Optional<List<Hotel>> findByCity(String cityName);
}
