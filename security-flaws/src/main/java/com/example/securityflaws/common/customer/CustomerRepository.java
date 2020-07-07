package com.example.securityflaws.common.customer;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Option<Customer> getById(UUID id);

    @NonNull
    @Override
    Seq<Customer> findAll();

    default Option<Customer> getById(String customerId, JdbcTemplate jdbcTemplate) {
        String query = "SELECT * FROM CUSTOMERS WHERE id='" + customerId + "'";
        List<Customer> customers = jdbcTemplate.query(query, this::resultSetToCustomer);

        return Option.of(customers.isEmpty() ? null : customers.get(0));
    }

    default Option<Customer> getById(String customerId, NamedParameterJdbcTemplate jdbcTemplate) {
        String query = "SELECT * FROM CUSTOMERS WHERE id=:id";
        List<Customer> customers = jdbcTemplate.query(query, Map.of("id", customerId), this::resultSetToCustomer);

        return Option.of(customers.isEmpty() ? null : customers.get(0));
    }

    private List<Customer> resultSetToCustomer(ResultSet rs) throws SQLException {
        List<Customer> result = new ArrayList<>();

        while (rs.next()) {
            var c = Customer.builder()
                    .budget(rs.getLong("BUDGET"))
                    .firstName(rs.getString("FIRST_NAME"))
                    .lastName(rs.getString("LAST_NAME"))
                    .sensitiveData(rs.getString("SENSITIVE_DATA"))
                    .build();

            result.add(c);
        }

        return result;
    }
}
