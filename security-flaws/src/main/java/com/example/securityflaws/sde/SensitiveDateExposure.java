package com.example.securityflaws.sde;

import com.example.securityflaws.common.api.dto.CustomerDTO;
import com.example.securityflaws.common.customer.CustomerController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/flaws/sde/customers")
@RequiredArgsConstructor
public class SensitiveDateExposure {

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
