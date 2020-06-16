package org.example.saga.customer.api.dto;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDToStringMapper {

    public String map(UUID u) {
        return u.toString();
    }

}
