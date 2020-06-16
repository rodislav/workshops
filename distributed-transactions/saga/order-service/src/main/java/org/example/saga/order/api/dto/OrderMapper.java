package org.example.saga.order.api.dto;

import org.example.saga.api.OrderDTO;
import org.example.saga.order.domain.Order;
import org.mapstruct.Mapper;

// https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDTO dto);

    OrderDTO toDto(Order entity);
}