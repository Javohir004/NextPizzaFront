package javohir.test.nextpizzafront.controller.admin;



import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.OrderClient;
import javohir.test.nextpizzafront.controller.BaseController;
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

    /**
     * Admin Dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            // TODO: Backend dan real statistika olish
            // Hozircha hardcoded

            model.addAttribute("totalOrders", 150);
            model.addAttribute("totalRevenue", new BigDecimal("15000000"));
            model.addAttribute("activeUsers", 45);
            model.addAttribute("todayOrders", 12);

            // Recent orders (hozircha bo'sh)
            model.addAttribute("recentOrders", List.of());

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
