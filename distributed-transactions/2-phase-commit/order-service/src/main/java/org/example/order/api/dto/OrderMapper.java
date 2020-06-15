package org.example.order.api.dto;

import org.example.order.domain.Order;
import org.example.order.generated.grpc.OrderRPC;
import org.example.order.generated.grpc.PlaceRPC;
import org.mapstruct.*;

// https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring", uses = {UUIDToStringMapper.class, StringToUUIDMapper.class})
public interface OrderMapper {

    Order toEntity(OrderDTO dto);

    OrderDTO toDto(Order entity);

    Order toEntity(PlaceRPC request);

    @Mappings({
            @Mapping(target = "message", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    OrderRPC toRPC(Order order);
}