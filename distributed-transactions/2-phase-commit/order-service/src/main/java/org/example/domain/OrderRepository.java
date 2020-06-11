package org.example.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    Option<Order> getById(UUID id);

    @NonNull
    @Override
    Seq<Order> findAll();
}
