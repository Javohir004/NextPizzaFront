package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public class BaseController {

    /**
     * Navbar uchun ma'lumotlarni qo'shish
     */
    protected void addNavbarAttributes(Model model, HttpServletRequest request) {
        // JWT token bor-yo'qligini tekshirish
        boolean isAuthenticated = isUserAuthenticated(request);
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            // TODO: Backend dan user ma'lumotlarini olish
            model.addAttribute("username", "User");  // JWT dan olish kerak
            model.addAttribute("balance", 50000);    // Backend dan olish kerak
            model.addAttribute("cartItemCount", 3);  // Cart dan olish kerak
        }
    }

    /**
     * Cookie dan JWT tokenni tekshirish
     */
    private boolean isUserAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName()) && cookie.getValue() != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
