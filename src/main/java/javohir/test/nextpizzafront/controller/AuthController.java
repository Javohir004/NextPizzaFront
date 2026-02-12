package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import javohir.test.nextpizzafront.client.AuthClient;
import javohir.test.nextpizzafront.dto.request.auth.LoginRequest;
import javohir.test.nextpizzafront.dto.request.auth.RegisterRequest;
import javohir.test.nextpizzafront.dto.response.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;

    /**
     * Login sahifasi
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    /**
     * Login qilish
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes) {
        try {
            AuthenticationResponse loginResponse = authClient.login(request);

            // JWT ni cookie ga saqlash (accessToken!)
            Cookie cookie = new Cookie("JWT_TOKEN", loginResponse.getAccessToken());  // ← O'zgardi
            cookie.setHttpOnly(true);
            cookie.setMaxAge(86400);
            cookie.setPath("/");
            response.addCookie(cookie);

            redirectAttributes.addFlashAttribute("success", "Xush kelibsiz!");

            return "redirect:/pizzas";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login yoki parol noto'g'ri!");
            return "redirect:/login";
        }
    }

    /**
     * Register sahifasi
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    /**
     * Ro'yxatdan o'tish
     */
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request,
                           RedirectAttributes redirectAttributes) {
        try {
            // Backend ga register so'rovi
            authClient.register(request);

            // Muvaffaqiyatli xabar
            redirectAttributes.addFlashAttribute("success",
                    "Muvaffaqiyatli ro'yxatdan o'tdingiz! Endi tizimga kiring.");

            return "redirect:/login";

        } catch (Exception e) {
            // Xato xabari
            redirectAttributes.addFlashAttribute("error",
                    "Ro'yxatdan o'tishda xatolik: " + e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * Logout (chiqish)
     */
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // Cookie ni o'chirish
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("success", "Tizimdan chiqdingiz!");
        return "redirect:/";
    }
}
