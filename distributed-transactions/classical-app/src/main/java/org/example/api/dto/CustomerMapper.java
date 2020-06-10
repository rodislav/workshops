package org.example.api.dto;

import org.example.customer.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDTO dto);
    @InheritInverseConfiguration
    CustomerDTO fromEntity(Customer entity);
}