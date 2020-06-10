package org.example.customer;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Option<Customer> getById(UUID id);

    Seq<Customer> findAll();
}
