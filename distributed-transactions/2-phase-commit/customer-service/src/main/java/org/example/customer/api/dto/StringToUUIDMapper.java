package org.example.customer.api.dto;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToUUIDMapper {

    public UUID map(String s) {
        return UUID.fromString(s);
    }

}
