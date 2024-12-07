package org.example.hotelmanagementpractice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Hotel name cannot be empty")
    @Size(min = 2, message = "Hotel name must have at least 2 characters")
//    <div th:if="${#fields.hasErrors('name')}" th:errors="*{name}" style="color: red;"></div>
    private String name;
    private String city;
    private String country;
    private String address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;
}
