package org.example.classicalapp.api;

import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import org.example.classicalapp.api.dto.CustomerDTO;
import org.example.classicalapp.api.dto.CustomerMapper;
import org.example.classicalapp.api.exception.NotFoundException;
import org.example.classicalapp.customer.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @GetMapping
    public Seq<CustomerDTO> list() {
        return customerService.findAll()
                .map(mapper::toDto);
    }

    @GetMapping("{id}")
    public CustomerDTO get(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(mapper::toDto)
                .getOrElseThrow(() -> new NotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody CustomerDTO dto, HttpServletResponse response) {
        return customerService.createCustomer(mapper.toEntity(dto))
                .peek(c -> response.addHeader(HttpHeaders.LOCATION, "/customers/" + c.getId()))
                .map(mapper::toDto)
                .get();
    }

}
