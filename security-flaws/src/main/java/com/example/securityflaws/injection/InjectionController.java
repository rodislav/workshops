package com.example.securityflaws.injection;

import com.example.securityflaws.common.api.dto.CustomerDTO;
import com.example.securityflaws.common.api.dto.CustomerMapper;
import com.example.securityflaws.common.api.exception.NotFoundException;
import com.example.securityflaws.common.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flaws/injection")
@RequiredArgsConstructor
public class InjectionController {

    private final CustomerService customerService;
    private final CustomerMapper mapper;

    /**
     * https://owasp.org/www-project-top-ten/OWASP_Top_Ten_2017/Top_10-2017_A1-Injection
     */
    @GetMapping("customerId/{id}")
    public CustomerDTO get(@PathVariable String id) {
        return customerService.findByIdJdbc(id)
                .map(mapper::toDto)
                .getOrElseThrow(() -> new NotFoundException(id));
    }

    @GetMapping("customerId/fix/{id}")
    public CustomerDTO getFix(@PathVariable String id) {
        return customerService.findByIdParamJdbc(id)
                .map(mapper::toDto)
                .getOrElseThrow(() -> new NotFoundException(id));
    }

}
