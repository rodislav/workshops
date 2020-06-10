package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    String firstName;
    String lastName;
    LocalDateTime created;
    Long budget;
    private UUID id;
}
