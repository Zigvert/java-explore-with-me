package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @NotNull(message = "Широта не может быть пустой")
    @Column(name = "lat", nullable = false)
    private Float lat;

    @NotNull(message = "Долгота не может быть пустой")
    @Column(name = "lon", nullable = false)
    private Float lon;
}
