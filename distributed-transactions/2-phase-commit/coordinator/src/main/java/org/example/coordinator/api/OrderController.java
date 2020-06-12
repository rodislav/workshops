package org.example.coordinator.api;

import lombok.RequiredArgsConstructor;
import org.example.coordinator.domain.OrderFacade;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderFacade orderFacade;

    //https://www.baeldung.com/spring-response-header
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO place(@RequestBody OrderDTO order, HttpServletResponse response) {
        return orderFacade.placeOrder(order)
                .peek(o -> response.addHeader(HttpHeaders.LOCATION, "/orders/" + o.getId()))
                .get();
    }

}
