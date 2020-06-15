package org.example.customer.api.dto;

import org.example.customer.domain.Customer;
import org.example.customer.generated.grpc.CustomerRPC;
import org.mapstruct.Mapper;

//https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring", uses = {UUIDToStringMapper.class, StringToUUIDMapper.class})
public interface CustomerMapper {

    CustomerRPC toRPC(Customer customer);

    Customer toEntity(CustomerDTO dto);

    CustomerDTO toDto(Customer entity);
}