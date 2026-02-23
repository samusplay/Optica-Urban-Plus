package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.Order;
import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.exceptions.BadRequestException;
import com.opticaApp.backend.exceptions.ResourceNotFoundException;
import com.opticaApp.backend.models.OrderItemResponse;
import com.opticaApp.backend.models.OrderRequestDTO;
import com.opticaApp.backend.models.OrderResponse;
import com.opticaApp.backend.models.OrderStatus;
import com.opticaApp.backend.repository.OrderRepository;
import com.opticaApp.backend.repository.UserRepository;
import com.opticaApp.backend.service.OrderItemService;
import com.opticaApp.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final OrderItemService orderItemService;
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

        //traformamos el metodo privado
        return orders.stream()
                .map(this::convertToResponse)
                .collect(java.util.stream.Collectors.toList());

    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequestDTO orderRequest, Long userId) {
        //Buscar usuario
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("Usuario no encontrado"));
        //crear la entidad
        Order order=Order.builder()
                .user(user)
                //luego actualiza pagado
                .estado(OrderStatus.PENDIENTE)
                .paymentMethod(orderRequest.getPaymentMethod())
                .shippingAddress(orderRequest.getShippingAddress())
                .total(BigDecimal.ZERO)
                .build();
        //guardar para obtener el Id
        Order savedOrder=orderRepository.save(order);

        //delegamos responsabilidad al servicio
        BigDecimal totalCalculado=orderItemService.processAnSaveItems(orderRequest.getItems(),savedOrder);

        //actualizar el total del final
        savedOrder.setTotal(totalCalculado);
        orderRepository.save(savedOrder);

        //convertir response
        return convertToResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId, Long userId) {
        log.info("Consultando detalle de la orden {} para el usuario {}", orderId, userId);
        //validamos el id
        Order order=orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(()->new RuntimeException("Pedido no encontrado"));

        return convertToResponse(order);


    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        log.info("Solicitud para cancelar orden {} del usuario {}", orderId, userId);

        //validar si existen la orden
        Order order=orderRepository.findByIdAndUserId(orderId ,userId)
                .orElseThrow(()->new ResourceNotFoundException("Pedido no encontrado"));

        //Debe estar pendiente para cancelar
        if(order.getEstado() !=OrderStatus.PENDIENTE){
            //usamos excepcion personalizada
            throw new BadRequestException("Solo de puede cancelar en estado pendiente");
        }
        //cambiamos estado
        order.setEstado(OrderStatus.CANCELADO);

        //devolvemos el stcok al comercio hacia la clase orderitemservice
        orderItemService.restoreStockForCanceledOrder(orderId);

        //guardamos en la base de datos
        orderRepository.save(order);
        log.info("Orden {} cancelada con éxito. Stock restaurado.", orderId);

    }
    // --- MÉTODOS PRIVADOS ---

    private OrderResponse convertToResponse(Order order) {

        // 1. Preparamos una lista vacía para los detalles
        List<OrderItemResponse> itemResponses = new java.util.ArrayList<>();

        // 2. Si la orden tiene items, los extraemos y transformamos
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            itemResponses = order.getItems().stream()
                    .map(item -> OrderItemResponse.builder()
                            // OJO: Si en tu entidad Product el ID se llama "id" en lugar de "productId", usa .getId()
                            .productId(item.getProduct().getProductId())
                            .nombreProducto(item.getProduct().getNombre())
                            .imagenProducto(item.getProduct().getImagenKey())
                            .cantidad(item.getCantidad())
                            .precioUnitario(item.getPrecioUnitario())
                            .subTotal(item.getSubTotal())
                            // Verificamos si hay fórmula para evitar NullPointerException
                            .prescriptionId(item.getPrescription() != null ? item.getPrescription().getId() : null)
                            .build())
                    .collect(java.util.stream.Collectors.toList());
        }

        // 3. Retornamos la orden completa, ¡ahora con la lista de ítems incluida!
        return OrderResponse.builder()
                .id(order.getId())
                .total(order.getTotal())
                .estado(order.getEstado())
                .metodoPago(order.getPaymentMethod())
                .shippingAddress(order.getShippingAddress())
                .fecha(order.getCreatedAt())
                .items(itemResponses) // ¡Esta es la clave!
                .build();
    }

}
