package io.github.shubhamtupe.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
