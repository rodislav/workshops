package org.example.customer.api.dto;

import org.example.customer.domain.Customer;
import org.example.order.generated.grpc.CustomerServiceOuterClass;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

//https://www.baeldung.com/mapstruct
@Mapper(componentModel = "spring", uses = {UUIDToStringMapper.class, StringToUUIDMapper.class})
public interface CustomerMapper {

    CustomerServiceOuterClass.CustomerRPC toRPC(Customer customer);

    Customer toEntity(CustomerDTO dto);

    @InheritInverseConfiguration
    CustomerDTO toDto(Customer entity);
}