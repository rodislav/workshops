package org.example.classicalapp.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
//https://www.baeldung.com/jackson-ignore-null-fields
public class CustomerDTO {
    String firstName;
    String lastName;
    LocalDateTime created;
    Long budget;
    private UUID id;
}
