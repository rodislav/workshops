package org.example.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public Order findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
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

    public Order markAsFailed(Order order, String message) {
        order.setStatus(OrderStatus.FAILED);
        order.setFailed(LocalDateTime.now());
        order.setMessage(message);
        return repository.save(order);
    }

    public Iterable<Order> findAll() {
        return repository.findAll();
    }
}
