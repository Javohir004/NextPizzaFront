package javohir.test.nextpizzafront.controller;

import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javohir.test.nextpizzafront.client.AuthClient;
import javohir.test.nextpizzafront.dto.request.auth.LoginRequest;
import javohir.test.nextpizzafront.dto.request.auth.RegisterRequest;
import javohir.test.nextpizzafront.dto.response.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthClient authClient;

    /**
     * Login sahifasi
     */
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        // Agar allaqachon login qilgan bo'lsa
        if (hasJwtCookie(request)) {
            return "redirect:/pizzas";
        }

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
            // Backend ga login so'rovi
            AuthenticationResponse authResponse = authClient.login(request);

            // JWT ni HttpOnly cookie ga saqlash
            Cookie jwtCookie = new Cookie("JWT_TOKEN", authResponse.getAccessToken());
            jwtCookie.setHttpOnly(true);  // XSS himoya
            jwtCookie.setSecure(false);    // Production da true
            jwtCookie.setMaxAge(86400);    // 1 kun
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            // Refresh token (ixtiyoriy)
            if (authResponse.getRefreshToken() != null) {
                Cookie refreshCookie = new Cookie("REFRESH_TOKEN", authResponse.getRefreshToken());
                refreshCookie.setHttpOnly(true);
                refreshCookie.setSecure(false);
                refreshCookie.setMaxAge(604800);  // 7 kun
                refreshCookie.setPath("/");
                response.addCookie(refreshCookie);
            }

            log.info("User logged in successfully: {}", request.getEmail());
            redirectAttributes.addFlashAttribute("success", "Xush kelibsiz!");

            return "redirect:/pizzas";

        } catch (FeignException.Unauthorized e) {
            log.warn("Login failed - invalid credentials: {}", request.getEmail());
            redirectAttributes.addFlashAttribute("error", "Email yoki parol noto'g'ri!");
            return "redirect:/login";

        } catch (FeignException e) {
            log.error("Login failed - server error: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Serverda xatolik. Qayta urinib ko'ring.");
            return "redirect:/login";

        } catch (Exception e) {
            log.error("Login failed - unexpected error: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
            return "redirect:/login";
        }
    }

    /**
     * Register sahifasi
     */
    @GetMapping("/register")
    public String registerPage(Model model, HttpServletRequest request) {
//        if (hasJwtCookie(request)) {
//            return "redirect:/pizzas";
//        }

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
            authClient.register(request);

            log.info("User registered successfully: {}", request.getEmail());
            redirectAttributes.addFlashAttribute("success",
                    "Muvaffaqiyatli ro'yxatdan o'tdingiz! Tizimga kiring.");

            return "redirect:/login";

        } catch (FeignException.Conflict e) {
            log.warn("Registration failed - email exists: {}", request.getEmail());
            redirectAttributes.addFlashAttribute("error",
                    "Bu email allaqachon ro'yxatdan o'tgan!");
            return "redirect:/register";

        } catch (FeignException e) {
            log.error("Registration failed: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error",
                    "Serverda xatolik. Qayta urinib ko'ring.");
            return "redirect:/register";

        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * Logout
     */
    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         RedirectAttributes redirectAttributes) {

        // JWT cookie ni o'chirish
        Cookie jwtCookie = new Cookie("JWT_TOKEN", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        // Refresh token ni ham o'chirish
        Cookie refreshCookie = new Cookie("REFRESH_TOKEN", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        log.info("User logged out");
        redirectAttributes.addFlashAttribute("success", "Tizimdan chiqdingiz!");

        return "redirect:/";
    }
}
