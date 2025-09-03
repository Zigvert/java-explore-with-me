package ru.practicum.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 250, message = "Name must be between 2 and 250 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Size(min = 6, max = 254, message = "Email must be between 6 and 254 characters")
    private String email;
}