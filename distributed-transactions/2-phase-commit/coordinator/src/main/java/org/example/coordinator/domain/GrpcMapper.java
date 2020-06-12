package org.example.coordinator.domain;

import org.example.coordinator.api.CustomerDTO;
import org.example.coordinator.api.OrderDTO;
import org.example.order.generated.grpc.CustomerServiceOuterClass;
import org.example.order.generated.grpc.OrderServiceOuterClass;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class GrpcMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    OrderServiceOuterClass.OrderRPC toRPC(OrderDTO order) {
        return OrderServiceOuterClass.OrderRPC.newBuilder()
                .setAmount(order.getAmount())
                .setCustomerId(order.getCustomerId().toString())
                .build();
    }

    OrderDTO toDto(OrderServiceOuterClass.OrderRPC orderRPC) {
        return OrderDTO.builder()
                .id(UUID.fromString(orderRPC.getId()))
                .customerId(UUID.fromString(orderRPC.getCustomerId()))
                .status(OrderStatus.valueOf(orderRPC.getStatus()))
                .amount(orderRPC.getAmount())
                .created(toDateTime(orderRPC.getCreated()))
                .placed(toDateTime(orderRPC.getPlaced()))
                .failed(toDateTime(orderRPC.getFailed()))
                .build();
    }

    CustomerServiceOuterClass.CustomerDebitRPC toRPC(UUID customerId, Long amount) {
        return CustomerServiceOuterClass.CustomerDebitRPC
                .newBuilder()
                .setCustomerId(customerId.toString())
                .setAmount(amount)
                .build();
    }

    CustomerDTO toDto(CustomerServiceOuterClass.CustomerRPC customerRPC) {
        return CustomerDTO
                .builder()
                .id(UUID.fromString(customerRPC.getId()))
                .firstName(customerRPC.getFirstName())
                .lastName(customerRPC.getLastName())
                .created(toDateTime(customerRPC.getCreated()))
                .build();
    }

    LocalDateTime toDateTime(String iso8601) {
        return LocalDateTime.from(formatter.parse(iso8601));
    }

}
