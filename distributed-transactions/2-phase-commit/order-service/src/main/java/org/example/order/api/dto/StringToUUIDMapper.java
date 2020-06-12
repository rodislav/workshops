package org.example.order.api.dto;

import java.util.UUID;

public class StringToUUIDMapper {

    public UUID map(String s) {
        return UUID.fromString(s);
    }

}
