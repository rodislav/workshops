package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrderDTO;
import org.example.order.Order;
import org.example.order.OrderFacade;
import org.example.order.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.example.api.dto.OrderDTO.fromEntity;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderFacade orderFacade;

    @GetMapping("{id}")
    public OrderDTO getOrder(UUID id) {
        final Order order = orderService.findById(id);

        return fromEntity(order);
    }

    @PostMapping()
    public void placeOrder(OrderDTO order, HttpServletResponse response) {
        final UUID uuid = orderFacade.placeOrder(order.toEntity());
        response.addHeader(HttpHeaders.LOCATION, "/order/" + uuid.toString());
    }

}
