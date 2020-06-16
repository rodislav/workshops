package org.example.saga.order.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Option<Order> findById(UUID id) {
        return repository.getById(id);
    }

    public Seq<Order> findAll() {
        return repository.findAll();
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        order.setCreated(LocalDateTime.now());

        return repository.save(order);
    }

    public Order placeOrder(Order order) {
        order.setStatus(OrderStatus.PLACED);
        order.setPlaced(LocalDateTime.now());

        return repository.save(order);
    }
}
