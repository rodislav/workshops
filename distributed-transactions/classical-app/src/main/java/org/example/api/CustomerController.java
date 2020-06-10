package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.CustomerDTO;
import org.example.customer.Customer;
import org.example.customer.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.api.dto.CustomerDTO.fromEntity;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> get() {
        var result = new ArrayList<CustomerDTO>();
        customerService.findAll()
                .forEach(order -> result.add(fromEntity(order)));

        return result;
    }

    @GetMapping("{id}")
    public CustomerDTO get(UUID id) {
        var customer = customerService.findById(id);

        return fromEntity(customer);
    }

    @PostMapping()
    public void placeOrder(@RequestBody CustomerDTO dto, HttpServletResponse response) {
        var uuid = customerService.createCustomer(dto.toEntity());
        response.addHeader(HttpHeaders.LOCATION, "/customer/" + uuid.toString());
    }

}
