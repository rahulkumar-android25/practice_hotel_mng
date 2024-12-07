package org.example.hotelmanagementpractice.web;

import lombok.AllArgsConstructor;
import org.example.hotelmanagementpractice.entity.Room;
import org.example.hotelmanagementpractice.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/get-rooms")
    public String getAllRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "room-list";
    }


    @GetMapping("/{id}")
    public String getRoomById(@PathVariable long id, Model model) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        model.addAttribute("room", room);
        return "room-details";
    }


    @GetMapping("/room-number/{roomNumber}")
    public String getRoomByRoomNumber(@PathVariable String roomNumber, Model model) {
        Room room = roomRepository.getRoomByRoomNumber(Integer.parseInt(roomNumber)).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        model.addAttribute("room", room);
        return "room-details";
    }

    @PostMapping
    public String addRoom(Room room, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "room-form";
        }
        roomRepository.save(room);
        redirectAttributes.addFlashAttribute("message", "Room added successfully");
        return "redirect:/rooms/get-rooms";
    }

    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        model.addAttribute("room", room);
        return "room-form";
    }

    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable long id, Room room, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "room-form";
        }
        room.setId(id);
        roomRepository.save(room);
        redirectAttributes.addFlashAttribute("message", "Room updated successfully");
        return "redirect:/rooms/get-rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoomById(@PathVariable long id, RedirectAttributes redirectAttributes) {
        roomRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Room deleted successfully");
        return "redirect:/rooms/get-rooms";
    }


    @GetMapping("/new")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "room-form";
    }

}
