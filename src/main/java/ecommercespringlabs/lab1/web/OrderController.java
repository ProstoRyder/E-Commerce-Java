package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.dto.order.OrderResponseDto;
import ecommercespringlabs.lab1.service.OrderService;
import ecommercespringlabs.lab1.service.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrder() {
        return ResponseEntity.ok(orderService.findAllOrder());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.findOrderById(id)));
    }
}
