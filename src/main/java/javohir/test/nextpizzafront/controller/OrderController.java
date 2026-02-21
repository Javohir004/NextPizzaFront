package javohir.test.nextpizzafront.controller;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.CartClient;
import javohir.test.nextpizzafront.client.OrderClient;
import javohir.test.nextpizzafront.dto.request.order.CreateOrderRequest;
import javohir.test.nextpizzafront.dto.response.cart.CartResponse;
import javohir.test.nextpizzafront.dto.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController extends BaseController {

    private final OrderClient orderClient;
    private final CartClient cartClient;

    /**
     * Checkout sahifasi (Buyurtma berish)
     */
    @GetMapping("/checkout")
    public String checkoutPage(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            // Savatni olish
            CartResponse cart = cartClient.getCart();

            // Savat bo'sh bo'lsa
            if (cart == null || cart.getItems().isEmpty()) {
                return "redirect:/cart";
            }

            model.addAttribute("cart", cart);
            model.addAttribute("orderRequest", new CreateOrderRequest());

        } catch (Exception e) {
            log.error("Error loading checkout: {}", e.getMessage());
            return "redirect:/cart";
        }

        return "user/checkout";
    }

    /**
     * Buyurtma berish
     */
    @PostMapping("/checkout")
    public String createOrder(@ModelAttribute CreateOrderRequest request,
                              RedirectAttributes redirectAttributes) {
      //  try {
            // Buyurtma yaratish
            OrderResponse order = orderClient.createOrder(request);

            log.info("Order created successfully: orderId={}", order.getId());

            redirectAttributes.addFlashAttribute("success",
                    "Buyurtma muvaffaqiyatli yaratildi! ID: " + order.getId());

            return "redirect:/my-orders";

//        } catch (FeignException.BadRequest e) {
//            log.error("Order creation failed - bad request: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("error",
//                    "Buyurtma yaratishda xatolik: Savat bo'sh yoki balans yetarli emas");
//            return "redirect:/cart";
//
//        } catch (FeignException e) {
//            log.error("Order creation failed: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("error",
//                    "Serverda xatolik. Qayta urinib ko'ring.");
//            return "redirect:/checkout";
//
//        } catch (Exception e) {
//            log.error("Unexpected error: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
//            return "redirect:/checkout";
//        }
    }

    /**
     * Mening buyurtmalarim
     */
    @GetMapping("/my-orders")
    public String myOrders(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            List<OrderResponse> orders = orderClient.getMyOrders();
            model.addAttribute("orders", orders != null ? orders : List.of());  // ← NULL CHECK

        } catch (Exception e) {
            log.error("Error loading orders: {}", e.getMessage());
            model.addAttribute("orders", List.of());  // ← BO'SH LIST
            model.addAttribute("error", "Buyurtmalarni yuklab bo'lmadi");
        }

        return "user/orders";
    }

    /**
     * Buyurtma detallari
     */
    @GetMapping("/orders/{orderId}")
    public String orderDetail(@PathVariable Long orderId,
                              Model model,
                              HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            OrderResponse order = orderClient.getOrderById(orderId);
            model.addAttribute("order", order);

        } catch (FeignException.NotFound e) {
            log.warn("Order not found: {}", orderId);
            return "redirect:/my-orders";

        } catch (Exception e) {
            log.error("Error loading order: {}", e.getMessage());
            model.addAttribute("error", "Buyurtmani yuklab bo'lmadi");
        }

        return "user/order-detail";
    }

    /**
     * Buyurtmani bekor qilish
     */
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId,
                              RedirectAttributes redirectAttributes) {
        try {
            orderClient.cancelOrder(orderId);

            log.info("Order cancelled: orderId={}", orderId);
            redirectAttributes.addFlashAttribute("success", "Buyurtma bekor qilindi");

        } catch (FeignException.BadRequest e) {
            log.warn("Cannot cancel order: {}", orderId);
            redirectAttributes.addFlashAttribute("error",
                    "Bu buyurtmani bekor qilib bo'lmaydi");

        } catch (Exception e) {
            log.error("Error cancelling order: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik yuz berdi");
        }

        return "redirect:/my-orders";
    }
}
