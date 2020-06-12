package org.example.customer.api.dto;

import org.example.customer.domain.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

//https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDTO dto);

    @InheritInverseConfiguration
    CustomerDTO toDto(Customer entity);
}