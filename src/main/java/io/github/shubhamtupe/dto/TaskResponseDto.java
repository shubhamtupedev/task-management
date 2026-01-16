package io.github.shubhamtupe.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskResponseDto {
    private String title;
    private String description;
    private String status;
}
