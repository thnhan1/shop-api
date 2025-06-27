package com.nhanab.shop.controller;

import com.nhanab.shop.dto.order.OrderDto;
import com.nhanab.shop.dto.order.OrderRequest;
import com.nhanab.shop.dto.order.UpdateOrderRequest;
import com.nhanab.shop.dto.payload.ErrorResponse;
import com.nhanab.shop.exception.ResourceNotFoundException;
import com.nhanab.shop.model.Order;
import com.nhanab.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Place a new order from user's shopping cart",
            description = "Creates a new order based on the items present in the user's shopping cart and specified delivery details. " +
                    "Returns the created order details.",
            responses =  {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Order successfully placed and created.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OrderDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request payload or validation error. E.g., missing required fields, invalid format.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = "{\"timestamp\": \"2025-06-27T10:30:00.000+07:00\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Validation error: customerName is required\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User's cart not found or product(s) in cart not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class,
                                            example = "{\"message\": \"User cart not found or product(s) in cart not found\"," +
                                                     "\"error\": \"Not Found\", \"code\": 404 }")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Insufficient stock for one or more products in the cart.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = "{\"timestamp\": \"2025-06-27T10:30:00.000+07:00\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"An unexpected error occurred.\"}")
                            )
                    )
            }
    )
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @Operation(
            summary = "Get order details by order Id",
            description = "Return order details by provided order id"
    )
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Not found order")
    })
    @GetMapping("/{id}")
    public OrderDto getOrder(@Parameter(description = "Id of order", required = true) @PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Get list order of user", description = "Get list of order of specific User id")
    @ApiResponse(responseCode = "200", description = "Successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersForUser(@Parameter(description = "Id of user", required = true) @PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersForUser(userId));
    }

    @Operation(summary = "Cancel order", description = "Cancel order by id if order status is PENDING")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found Order"),
            @ApiResponse(responseCode = "400", description = "Bad Request be cause order status is not PENDING")
    })
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable("id") UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().body("Order has been cancelled");
    }

    @Operation(summary = "Update order info", description = "Update customer info or shipping address")
    @ApiResponse(responseCode = "200", description = "Update successfully")
    @PutMapping
    public OrderDto updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest) {
        return orderService.updateOrder(updateOrderRequest);
    }
}
