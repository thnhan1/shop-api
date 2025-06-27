package com.nhanab.shop.mapper;

import com.nhanab.shop.dto.order.OrderDto;
import com.nhanab.shop.dto.order.OrderItemDto;
import com.nhanab.shop.model.Order;
import com.nhanab.shop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "id", target = "orderId")
    OrderDto toDto(Order order);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source="quantity", target = "quantity")
    @Mapping(source = "price", target = "unitPrice")
    OrderItemDto toDto(OrderItem orderItem);


}
