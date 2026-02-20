package javohir.test.nextpizzafront.controller.admin;



import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.OrderClient;
import javohir.test.nextpizzafront.controller.BaseController;
import javohir.test.nextpizzafront.dto.request.order.UpdateOrderStatusRequest;
import javohir.test.nextpizzafront.dto.response.order.OrderResponse;
import javohir.test.nextpizzafront.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController extends BaseController {

    private final OrderClient orderClient;

    /**
     * Barcha buyurtmalar
     */
    @GetMapping
    public String listOrders(@RequestParam(required = false) String status, Model model,
                                                             HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            List<OrderResponse> orders;

            if (status != null && !status.isEmpty()) {
                // Filter by status
                orders = orderClient.getOrdersByStatus(status);
            } else {
                // All orders
                orders = orderClient.getAllOrders();
            }

            model.addAttribute("orders", orders);
            model.addAttribute("selectedStatus", status);

        } catch (Exception e) {
            log.error("Error loading orders: {}", e.getMessage());
            model.addAttribute("error", "Buyurtmalarni yuklab bo'lmadi");
            model.addAttribute("orders", List.of());
        }

        return "admin/orders";
    }

    /**
     * Buyurtma detallari
     */
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model,
                                           HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            OrderResponse order = orderClient.getOrderById(id);
            model.addAttribute("order", order);

        } catch (Exception e) {
            log.error("Error loading order: {}", e.getMessage());
            return "redirect:/admin/orders";
        }

        return "admin/order-detail";
    }

    /**
     * Buyurtma statusini yangilash
     */
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status,
                                                RedirectAttributes redirectAttributes) {
        try {
            UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
            // shu joyiga qarash kerak muammo chiqarishi mumkin
            request.setStatus(OrderStatus.valueOf(status));

            orderClient.updateOrderStatus(id, request);

            log.info("Order status updated: {} -> {}", id, status);
            redirectAttributes.addFlashAttribute("success", "Status yangilandi!");

        } catch (Exception e) {
            log.error("Error updating order status: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
        }

        return "redirect:/admin/orders/" + id;
    }
}