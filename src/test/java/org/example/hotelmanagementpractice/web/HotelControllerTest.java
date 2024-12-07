package org.example.hotelmanagementpractice.web;

import org.example.hotelmanagementpractice.entity.Hotel;
import org.example.hotelmanagementpractice.entity.Room;
import org.example.hotelmanagementpractice.repositories.HotelRepository;
import org.hibernate.annotations.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class HotelControllerTest {

    Hotel hotel;

//    @Autowired
    public MockMvc mockMvc;

    @Mock
    HotelRepository hotelRepository;

    @Mock
    View mockView;

    @InjectMocks
    HotelController hotelController;

    @BeforeEach
    void setUp() throws ParseException {
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel 1");
        hotel.setAddress("Hotel Address");
        hotel.setCity("Hotel City");
        hotel.setCountry("Hotel Country");
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(hotelController).build();
    }


    @Test
    void getAllHotels() throws Exception {
//        pare test data (mock hotels and rooms)
        Room room1 = new Room(1L, 101, 150.0, true, 2, null);  // Room 1 (null for hotel)
        Room room2 = new Room(2L, 102, 120.0, false, 2, null); // Room 2 (null for hotel)

        Hotel hotel1 = new Hotel(1L, "Hotel One", "City A", "Country A", "Address One", Arrays.asList(room1, room2));
        hotel1.getRooms().forEach(room -> room.setHotel(hotel1));  // Set hotel reference for rooms

        Room room3 = new Room(3L, 201, 200.0, true, 4, null); // Room 3
        Hotel hotel2 = new Hotel(2L, "Hotel Two", "City B", "Country B", "Address Two", Arrays.asList(room3));
        hotel2.getRooms().forEach(room -> room.setHotel(hotel2));  // Set hotel reference for rooms

        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        // Mock the behavior of hotelRepository.findAll() to return the test data
        when(hotelRepository.findAll()).thenReturn(hotels);

        // Perform the GET request to "/"
        mockMvc.perform(get("/"))
                .andExpect(view().name("hotel-list"))  // Expect the view to be "hotel-list"
                .andExpect(model().attribute("hotels", hotels));  // Expect the model to contain the "hotels" attribute with the test data

        // Optionally, verify that the repository method was called once
//        verify(hotelRepository, times(1)).findAll();

    }

    @Test
    void getHotelById() {
    }

    @Test
    void getHotelByCity() {
    }

    @Test
    void addHotel() {
    }

    @Test
    void updateHotel() {
    }

    @Test
    void showEditForm() {
    }

    @Test
    void deleteHotelById() {
    }

    @Test
    void showAddHotelForm() {
    }

    @Test
    void showHotelRoomForm() {
    }

    @Test
    void addRoom() {
    }

    @Test
    void showRoomsList() {
    }
}