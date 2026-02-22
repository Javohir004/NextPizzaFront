package javohir.test.nextpizzafront.controller.admin;



import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.OrderClient;
import javohir.test.nextpizzafront.client.UserClient;
import javohir.test.nextpizzafront.controller.BaseController;
import javohir.test.nextpizzafront.dto.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController extends BaseController {

    private final OrderClient orderClient;
    private final UserClient userClient;

    /**
     * Admin Dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            // TODO: Backend dan real statistika olish
            // Hozircha hardcoded
            Long orderCounts = orderClient.getOrderCounts();
            Long todayOrderCounts = orderClient.getTodayOrderCounts();
            Long userCounts = userClient.getUserCount();
            List<OrderResponse> todaysOrders = orderClient.getTodayOrder();

            model.addAttribute("totalOrders", orderCounts);
            model.addAttribute("totalRevenue", new BigDecimal("15000000"));
            model.addAttribute("activeUsers", userCounts);
            model.addAttribute("todayOrders", todayOrderCounts);

            // Recent orders (hozircha bo'sh)
            model.addAttribute("recentOrders", todaysOrders);

            // TODO: Backend ga statistics endpoint qo'shish kerak
            // GET /api/admin/statistics
            // GET /api/admin/orders/recent?limit=10

        } catch (Exception e) {
            log.error("Error loading dashboard: {}", e.getMessage());
            model.addAttribute("error", "Ma'lumotlarni yuklab bo'lmadi");
        }

        return "admin/dashboard";
    }
}
