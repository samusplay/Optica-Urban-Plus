package com.opticaApp.backend.repository;

import com.opticaApp.backend.entity.Order;
import com.opticaApp.backend.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //Traer todos los pedidos de un usuario, los más nuevos primero
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    //buscar un pedido especifico
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    //Ver solo pedidos "PENDIENTES" o "ENVIADOS" de un usuario
    List<Order> findByUserIdAndEstado(Long userId, OrderStatus estado);
}
