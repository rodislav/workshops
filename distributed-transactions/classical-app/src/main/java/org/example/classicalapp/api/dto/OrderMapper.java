package org.example.classicalapp.api.dto;

import org.example.classicalapp.order.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

// https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDTO dto);

    @InheritInverseConfiguration
    OrderDTO toDto(Order entity);
}