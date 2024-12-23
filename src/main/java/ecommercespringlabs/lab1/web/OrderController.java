package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.dto.order.OrderEntryRequestDto;
import ecommercespringlabs.lab1.dto.order.OrderRequestDto;
import ecommercespringlabs.lab1.dto.order.OrderResponseDto;
import ecommercespringlabs.lab1.service.OrderService;
import ecommercespringlabs.lab1.service.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto, OrderEntryRequestDto orderEntryRequestDto) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.addOrder(orderRequestDto, orderEntryRequestDto)));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderMapper.toOrderResponseDtoList(orderService.findAllOrder()));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.findOrderById(orderId)));
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
    }

}