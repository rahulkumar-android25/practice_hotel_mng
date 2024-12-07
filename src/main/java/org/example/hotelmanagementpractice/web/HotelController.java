package org.example.hotelmanagementpractice.web;

import lombok.AllArgsConstructor;
import org.example.hotelmanagementpractice.entity.Hotel;
import org.example.hotelmanagementpractice.entity.Room;
import org.example.hotelmanagementpractice.repositories.HotelRepository;
import org.example.hotelmanagementpractice.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
//@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;


    @GetMapping( "/index")
    public String getAllHotels(Model model) {
        List<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
        return "hotel-list";
    }

    @GetMapping("/hotels/{id}")
    public String getHotelById(@PathVariable Long id, Model model) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        model.addAttribute("hotel", hotel);
        return "hotel-details";
    }


    @GetMapping("/hotels/city/{city}")
    public String getHotelByCity(@PathVariable String city, Model model) {
        List<Hotel> hotels = hotelRepository.findByCity(city).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        model.addAttribute("hotel", hotels);
        return "hotel-details";
    }

    @PostMapping("/hotels")
    public String addHotel(Hotel hotel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "hotel-form";
        }
        System.out.println("Hotel added"+hotel.getName()+hotel.getAddress()+hotel.getCity()+hotel.getCountry());
        hotelRepository.save(hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel added successfully");
        return "redirect:/";
    }


    @PostMapping("/hotels/edit/{id}")
    public String updateHotel(@PathVariable Long id, Hotel hotel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            System.out.println("ErrorEdit hotel"+result.getAllErrors());
            return "hotel-form";
        }
        hotel.setId(id);
        hotelRepository.save(hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel updated successfully");
        System.out.println("Edit hotel"+hotel.getName()+hotel.getAddress());
        return "redirect:/";
    }

    @GetMapping("/hotels/edit/{id}")
    public String showEditForm(@PathVariable Long id,  Model model, RedirectAttributes redirectAttributes) {
        Hotel hotel=hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        model.addAttribute("hotel", hotel);
        return "hotel-form";
    }

    @GetMapping("/hotels/delete/{id}")
    public String deleteHotelById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Hotel deleted successfully");
        return "redirect:/";
    }

    @GetMapping("/hotels/new")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hotel-form";
    }

    @GetMapping("/hotels/new/room/{id}")
    public String showHotelRoomForm(@PathVariable long id, Model model){
        System.out.println("Hotel room page added"+id);
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("hotelId", id);
        model.addAttribute("room", new Room());
        return "hotel-room-form";
    }


    @PostMapping("/hotels/add-room/{id}")
    public String addRoom(@PathVariable String id, @ModelAttribute Room room, RedirectAttributes redirectAttributes) {
        System.out.println("add-room/{id}-->"+id +"roomId"+room.getId()+ "priceNight"+room.getPricePerNight() +"capacity"+ room.getCapacity()+"roomNumber"+ room.getRoomNumber());

        Room roomF= roomRepository.findById(room.getId()).orElseThrow(()-> new IllegalArgumentException("Room not found!!"));

        Hotel hotel=hotelRepository.findById(Long.parseLong(id)).orElseThrow(()-> new IllegalArgumentException("Hotel not found"));
        roomF.setHotel(hotel);
        roomRepository.save(roomF);
        redirectAttributes.addFlashAttribute("message", "Room added successfully");
        return "redirect:/";
    }

    @GetMapping("/hotels/rooms/{id}")
    public String showRoomsList(@PathVariable Long id, Model model) {
        List<Room> rooms=roomRepository.getRoomsByHotelId(id).orElseThrow(()-> new IllegalArgumentException("Hotel not found"));
        model.addAttribute("rooms", rooms);
        model.addAttribute("hotelId", id);
        return "hotel-room-list";
    };

}
