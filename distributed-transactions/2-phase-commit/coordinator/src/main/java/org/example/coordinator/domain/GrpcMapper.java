package org.example.coordinator.domain;

import org.example.coordinator.api.CustomerDTO;
import org.example.coordinator.api.OrderDTO;
import org.example.order.generated.grpc.CustomerServiceOuterClass;
import org.example.order.generated.grpc.CustomerServiceOuterClass.CustomerDebitRPC;
import org.example.order.generated.grpc.CustomerServiceOuterClass.CustomerRPC;
import org.example.order.generated.grpc.OrderServiceOuterClass;
import org.example.order.generated.grpc.OrderServiceOuterClass.OrderRPC;
import org.example.order.generated.grpc.OrderServiceOuterClass.PlaceOrderRPC;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class GrpcMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    PlaceOrderRPC toRPC(OrderDTO order) {
        return PlaceOrderRPC.newBuilder()
                .setAmount(order.getAmount())
                .setCustomerId(order.getCustomerId().toString())
                .build();
    }

    OrderDTO toDto(OrderRPC orderRPC) {
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

    CustomerDebitRPC toRPC(UUID customerId, Long amount) {
        return CustomerDebitRPC
                .newBuilder()
                .setCustomerId(customerId.toString())
                .setAmount(amount)
                .build();
    }

    CustomerDTO toDto(CustomerRPC customerRPC) {
        return CustomerDTO
                .builder()
                .id(UUID.fromString(customerRPC.getId()))
                .firstName(customerRPC.getFirstName())
                .lastName(customerRPC.getLastName())
                .created(toDateTime(customerRPC.getCreated()))
                .build();
    }

    @Nullable
    LocalDateTime toDateTime(@Nullable String iso8601) {
        if(iso8601 == null || iso8601.length() < 1) {
            return null;
        }

        return LocalDateTime.from(formatter.parse(iso8601));
    }

}
