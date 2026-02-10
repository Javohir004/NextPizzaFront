package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.order.CreateOrderRequest;
import javohir.test.nextpizzafront.dto.request.order.UpdateOrderStatusRequest;
import javohir.test.nextpizzafront.dto.response.order.OrderResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}")
public interface OrderClient {

    @PostMapping("/orders")
    OrderResponse createOrder(@RequestBody CreateOrderRequest request);

    @GetMapping("/orders/my-orders")
    List<OrderResponse> getMyOrders();

    @GetMapping("/orders/{orderId}")
    OrderResponse getOrderById(@PathVariable Long orderId);

    @PutMapping("/orders/{orderId}/cancel")
    OrderResponse cancelOrder(@PathVariable Long orderId);

    // Admin endpoints
    @GetMapping("/orders/admin/all")
    List<OrderResponse> getAllOrders();

    @PutMapping("/orders/admin/{orderId}/status")
    OrderResponse updateStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request);
}
