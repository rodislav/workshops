package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrderDTO;
import org.example.order.Order;
import org.example.order.OrderFacade;
import org.example.order.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.api.dto.OrderDTO.fromEntity;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderFacade orderFacade;

    @GetMapping
    public List<OrderDTO> get() {
        var result = new ArrayList<OrderDTO>();
        orderService.findAll()
                .forEach(order -> result.add(fromEntity(order)));

        return result;
    }

    @GetMapping("{id}")
    public OrderDTO get(UUID id) {
        var order = orderService.findById(id);

        return fromEntity(order);
    }

    @PostMapping()
    public void place(@RequestBody OrderDTO order, HttpServletResponse response) {
        var uuid = orderFacade.placeOrder(order.toEntity());
        response.addHeader(HttpHeaders.LOCATION, "/order/" + uuid.toString());
    }

}
