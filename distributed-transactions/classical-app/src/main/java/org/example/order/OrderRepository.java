package org.example.order;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    Option<Order> getById(UUID id);

    Seq<Order> findAll();
}
