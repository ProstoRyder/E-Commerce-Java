package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.dto.order.OrderEntryDto;
import ecommercespringlabs.lab1.dto.order.OrderEntryRequestDto;
import ecommercespringlabs.lab1.dto.order.OrderRequestDto;
import ecommercespringlabs.lab1.repository.CustomerRepository;
import ecommercespringlabs.lab1.repository.OrderRepository;
import ecommercespringlabs.lab1.repository.ProductRepository;
import ecommercespringlabs.lab1.repository.entity.CustomerEntity;
import ecommercespringlabs.lab1.repository.entity.OrderEntity;
import ecommercespringlabs.lab1.repository.entity.OrderEntryEntity;
import ecommercespringlabs.lab1.repository.entity.ProductEntity;
import ecommercespringlabs.lab1.service.OrderService;
import ecommercespringlabs.lab1.service.exception.CustomerNotFoundException;
import ecommercespringlabs.lab1.service.exception.OrderNotFoundException;
import ecommercespringlabs.lab1.service.exception.ProductNotFoundException;
import ecommercespringlabs.lab1.service.mapper.OrderMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public Order findOrderById(UUID id) {
        OrderEntity order = orderRepository.findByNaturalId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toOrder(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrder() {
        return orderMapper.toOrderList(orderRepository.findAll());
    }


    @Override
    @Transactional
    public Order addOrder(OrderRequestDto orderRequestDto, OrderEntryRequestDto orderEntryRequestDto) {
        Double totalPrice = 0.0;

        UUID customerId = UUID.fromString(orderRequestDto.getCustomerId());

        CustomerEntity customer = customerRepository.findByNaturalId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));

        List<OrderEntryEntity> orderEntries = new ArrayList<>();

        for (OrderEntryDto entryRequestDto : orderRequestDto.getOrderEntries()) {
            if (entryRequestDto.getProduct() == null || entryRequestDto.getProduct().isBlank()) {
                throw new IllegalArgumentException("Product ID cannot be null or empty");
            }
            UUID productId = UUID.fromString(entryRequestDto.getProduct());

            ProductEntity product = productRepository.findByNaturalId(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderEntryEntity orderEntry = OrderEntryEntity.builder()
                    .count(entryRequestDto.getCount())
                    .product(product)
                    .build();

            orderEntries.add(orderEntry);

            totalPrice += product.getPrice() * entryRequestDto.getCount();
        }

        OrderEntity newOrder = OrderEntity.builder()
                .totalPrice(totalPrice)
                .order_entries(orderEntries)
                .customer(customer)
                .orders_reference(UUID.randomUUID())
                .orderStatus(OrderStatus.PERFORMED)
                .build();

        orderEntries.forEach(entry -> entry.setOrder(newOrder));
        try {
            return orderMapper.toOrder(orderRepository.save(newOrder));
        } catch (Exception e) {
            throw new PersistenceException("Failed to save order", e);
        }
    }


    @Override
    @Transactional
    public void deleteOrder(UUID id) {
        findOrderById(id);
        try {
            orderRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
