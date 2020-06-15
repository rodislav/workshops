package org.example.classicalapp.api;

import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import org.example.classicalapp.api.dto.OrderDTO;
import org.example.classicalapp.api.dto.OrderMapper;
import org.example.classicalapp.api.exception.NotFoundException;
import org.example.classicalapp.order.OrderFacade;
import org.example.classicalapp.order.OrderService;
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
