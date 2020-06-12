package org.example.customer.api.dto;

import java.util.UUID;

public class StringToUUIDMapper {

    public UUID map(String s) {
        return UUID.fromString(s);
    }

}
