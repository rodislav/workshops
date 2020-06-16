package org.example.saga.customer.api.dto;

import org.example.saga.api.CustomerDTO;
import org.example.saga.customer.domain.Customer;
import org.mapstruct.Mapper;

//https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring", uses = {UUIDToStringMapper.class, StringToUUIDMapper.class})
public interface CustomerMapper {

    Customer toEntity(CustomerDTO dto);

    CustomerDTO toDto(Customer entity);
}