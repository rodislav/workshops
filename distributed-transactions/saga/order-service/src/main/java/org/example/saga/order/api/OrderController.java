package org.example.saga.order.api;

import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import org.example.saga.api.OrderDTO;
import org.example.saga.order.api.dto.OrderMapper;
import org.example.saga.order.api.exception.NotFoundException;
import org.example.saga.order.domain.OrderFacade;
import org.example.saga.order.domain.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderFacade orderFacade;
    private final OrderMapper mapper;

    @GetMapping
    public Seq<OrderDTO> list() {
        return orderService.findAll()
                .map(mapper::toDto);
    }

    @GetMapping("{id}")
    public OrderDTO get(@PathVariable UUID id) {
        return orderService.findById(id)
                .map(mapper::toDto)
                .getOrElseThrow(() -> new NotFoundException(id));
    }

    //https://www.baeldung.com/spring-response-header
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO place(@RequestBody OrderDTO order, HttpServletResponse response) {
        return orderFacade.placeOrder(mapper.toEntity(order))
                .peek(o -> response.addHeader(HttpHeaders.LOCATION, "/orders/" + o.getId()))
                .map(mapper::toDto)
                .get();
    }

}
