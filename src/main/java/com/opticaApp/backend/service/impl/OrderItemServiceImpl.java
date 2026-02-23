package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.Order;
import com.opticaApp.backend.entity.OrderItem;
import com.opticaApp.backend.entity.Prescription;
import com.opticaApp.backend.entity.Product;

import com.opticaApp.backend.exceptions.BadRequestException;
import com.opticaApp.backend.exceptions.NotFoundException;
import com.opticaApp.backend.models.OrderItemRequestDTO;
import com.opticaApp.backend.models.OrderRequestDTO;
import com.opticaApp.backend.repository.OrderItemRepository;
import com.opticaApp.backend.repository.PrescriptionRepository;
import com.opticaApp.backend.repository.ProductRepository;
import com.opticaApp.backend.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    //repositories
    private final ProductRepository productRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public BigDecimal processAnSaveItems(List<OrderItemRequestDTO> itemsDto, Order saveOrder) {
        BigDecimal total=BigDecimal.ZERO;
        //instaciamos  nuevo arreglo
        List<OrderItem>orderItemsToSave=new ArrayList<>();

        for(OrderItemRequestDTO itemDto:itemsDto){
            //buscar el producto para sacar el precio real y stock
            Product product=productRepository.findById(itemDto.getProductId())
                    .orElseThrow(()->new NotFoundException("Producto no encontrado con ID"+itemDto.getProductId()));

            //validar que haya stock sufienciente
            if(product.getStock()<itemDto.getCantidad()){
                throw  new BadRequestException("Stock insuficiente para el producto"+product.getNombre());
            }
            //buscar por formula medica
            Prescription prescription=null;

            if(itemDto.getPrescriptionId() !=null){
                prescription=prescriptionRepository.findById(itemDto.getPrescriptionId())
                        .orElseThrow(()-> new NotFoundException("Formula medica no encontrada"));
            }
            //calcular el subtotal precioDb*¨cantidad enviada
            BigDecimal subTotal=product.getPrecio().multiply(BigDecimal.valueOf(itemDto.getCantidad()));

            //sumar el gran total
            total=total.add(subTotal);

            //construir la entidad

            OrderItem orderItem=OrderItem.builder()
                    .order(saveOrder)
                    .product(product)
                    .prescription(prescription)
                    .cantidad(itemDto.getCantidad())
                    .precioUnitario(product.getPrecio())
                    .subTotal(subTotal)
                    .build();
            //guardado
            orderItemsToSave.add(orderItem);

            //descontar el stock
            product.setStock(product.getStock()-itemDto.getCantidad());
            productRepository.save(product);

        }
        //guardar los items
        orderItemRepository.saveAll(orderItemsToSave);

        //retornamos el total
        return total;


    }

    @Override
    @Transactional
    public void restoreStockForCanceledOrder(Long orderId) {
        //buscamos los items
        List<OrderItem>items=orderItemRepository.findByOrderId(orderId);

        //devolver la cantidades del inventario
        for(OrderItem item :items){
            //creamos un objeto para recorrer
            Product product=item.getProduct();
            product.setStock(product.getStock()+item.getCantidad());
            productRepository.save(product);
        }


    }
}
