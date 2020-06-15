package org.example.coordinator.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.domain.OrderFacade;
import org.example.coordinator.domain.OrderPlacementException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
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

    @ExceptionHandler(OrderPlacementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleOrderPlacementException(HttpServletRequest req, OrderPlacementException e) {
        log.error("Request: " + req.getRequestURL() + " raised " + e);

        Map<String, String> mav = new HashMap<>();
        mav.put("exception", e.getMessage());

        return mav;
    }

}
