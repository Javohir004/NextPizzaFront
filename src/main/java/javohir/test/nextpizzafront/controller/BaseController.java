package javohir.test.nextpizzafront.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.CartClient;
import javohir.test.nextpizzafront.client.UserClient;
import javohir.test.nextpizzafront.dto.response.UserResponse;
import javohir.test.nextpizzafront.dto.response.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.Arrays;


public class BaseController {

    @Autowired
    private  UserClient userClient;

    @Autowired
    private CartClient cartClient;

    /**
     * Navbar uchun ma'lumotlarni qo'shish
     */
    protected void addNavbarAttributes(Model model, HttpServletRequest request) {
        if (hasJwtCookie(request)) {
            try {
                // Backend dan user ma'lumotlarini olish
                UserResponse user = userClient.getCurrentUser();
                // Cart count olish
                int cartCount = 0;
                try {
                    CartResponse cart = cartClient.getCart();
                    cartCount = cart.getTotalItems() != null ? cart.getTotalItems() : 0;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }

                model.addAttribute("isAuthenticated", true);
                model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
                model.addAttribute("balance", user.getBalance());
                model.addAttribute("userId", user.getId());
                model.addAttribute("userRole", user.getRole());
                model.addAttribute("cartItemCount", cartCount);  // TODO: Cart dan olish

            } catch (Exception e) {
                // JWT invalid yoki expired - logout
                model.addAttribute("isAuthenticated", false);
            }
        } else {
            model.addAttribute("isAuthenticated", false);
        }
    }

    /**
     * Cookie da JWT bormi?
     */
    protected boolean hasJwtCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName()) &&
                        cookie.getValue() != null &&
                        !cookie.getValue().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cookie dan JWT tokenni olish
     */
    protected String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
