package org.example.order.api.dto;

import org.example.order.domain.Order;
import org.example.order.generated.grpc.OrderServiceOuterClass;
import org.example.order.generated.grpc.OrderServiceOuterClass.OrderRPC;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

// https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring", uses = {UUIDToStringMapper.class, StringToUUIDMapper.class})
public interface OrderMapper {

    Order toEntity(OrderDTO dto);

    @InheritInverseConfiguration
    OrderDTO toDto(Order entity);

    Order toEntity(OrderRPC request);

    OrderRPC toRPC(Order order);
}