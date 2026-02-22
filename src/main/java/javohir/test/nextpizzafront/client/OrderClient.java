package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.order.CreateOrderRequest;
import javohir.test.nextpizzafront.dto.request.order.UpdateOrderStatusRequest;
import javohir.test.nextpizzafront.dto.response.order.OrderResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}")
public interface OrderClient {

    @PostMapping("order/create-orders")
    OrderResponse createOrder(@RequestBody CreateOrderRequest request);

    @GetMapping("/order/my-orders")
    List<OrderResponse> getMyOrders();

    @GetMapping("/order/{orderId}")
    OrderResponse getOrderById(@PathVariable Long orderId);

    @PutMapping("/order/{orderId}/cancel")
    OrderResponse cancelOrder(@PathVariable Long orderId);


    // Admin endpoints
    @GetMapping("/order/admin/all")
    List<OrderResponse> getAllOrders();

    @GetMapping("/order/admin/status/{status}")
    List<OrderResponse> getOrdersByStatus(@PathVariable String status);

    @PutMapping("/order/admin/{orderId}/status")
    OrderResponse updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request);

    @GetMapping("/order/get-order-counts")
    Long getOrderCounts();

    @GetMapping("/order/get-today's-order-counts")
    Long getTodayOrderCounts();


}
