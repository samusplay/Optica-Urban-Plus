package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.Order;
import com.opticaApp.backend.exceptions.ResourceNotFoundException;
import com.opticaApp.backend.models.OrderResponse;
import com.opticaApp.backend.repository.OrderRepository;
import com.opticaApp.backend.repository.UserRepository;
import com.opticaApp.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//Logs
@Slf4j
public class OrderServiceImpl implements OrderService {
    //inyectamos repo
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(Long userId) {
        //registramos log
        log.info("Obteniendo historial de pedidos para el usuario ID: {}", userId);

        //validacion de seguridad si existe el usuario
        if(!userRepository.existsById(userId)){
            throw  new ResourceNotFoundException("Usuario no encontrado con ID: " + userId);

        }

        //buscamos en la base de datos
        List<Order>orders=orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        //traformamos con stream de entity a dto
        return orders.stream()
                .map(order ->{
                    return OrderResponse.builder()
                            .id(order.getId())
                            .total(order.getTotal())
                            .estado(order.getEstado())
                            .metodoPago(order.getPaymentMethod())
                            .shippingAddress(order.getShippingAddress())
                            .fecha(order.getCreatedAt())
                            .build();

                })
                .collect(Collectors.toList()); //convertimos en una lista real
    }
}
