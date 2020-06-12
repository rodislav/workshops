package org.example.classicalapp.api.dto;

import org.example.classicalapp.customer.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

//https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDTO dto);

    @InheritInverseConfiguration
    CustomerDTO toDto(Customer entity);
}