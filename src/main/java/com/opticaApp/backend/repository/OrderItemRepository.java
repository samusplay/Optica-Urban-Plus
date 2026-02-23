package com.opticaApp.backend.repository;
import com.opticaApp.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    //encontrar por id
    List<OrderItem>findByOrderId(Long orderId);

    //que Producto se compro con la formula
    List<OrderItem>findByPrescriptionId(Long prescriptionId);

    //Si un usuario en especifico ya habia comprado un producto
   // List<OrderItem>findByOrder_UserIdAndProductId(Long userId,Long productId);
}
