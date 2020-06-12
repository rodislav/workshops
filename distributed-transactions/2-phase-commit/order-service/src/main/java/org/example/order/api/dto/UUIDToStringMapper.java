package org.example.order.api.dto;

import java.util.UUID;

public class UUIDToStringMapper {

    public String map(UUID u) {
        return u.toString();
    }

}
