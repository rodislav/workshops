package com.example.securityflaws.ba;

import com.example.securityflaws.common.api.dto.CustomerDTO;
import com.example.securityflaws.common.api.dto.CustomerMapper;
import com.example.securityflaws.common.api.exception.NotFoundException;
import com.example.securityflaws.common.customer.CustomerController;
import com.example.securityflaws.common.customer.CustomerService;
import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/flaws/ba/customers")
@RequiredArgsConstructor
public class BrokenAuthenticationController {

    CustomerController customerController;

    @GetMapping("{id}")
    public CustomerDTO get(@PathVariable UUID id) {
        return customerController.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody CustomerDTO dto, HttpServletResponse response) {
        return customerController.create(dto, response);
    }
}
