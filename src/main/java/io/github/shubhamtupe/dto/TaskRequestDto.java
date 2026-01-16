package io.github.shubhamtupe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskRequestDto {

    @NotBlank(message = "Title must not be blank or empty")
    @Size(max = 35, message = "Title must not exceed 35 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Title must contain only alphabets and spaces"
    )
    private String title;

    @NotBlank(message = "Description must not be blank or empty")
    @Size(max = 200, message = "Description must not exceed 200 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Description must contain only alphabets and spaces"
    )
    private String description;

    @NotBlank(message = "Status must not be blank or empty")
    private String status;
}
