package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.UserClient;
import javohir.test.nextpizzafront.dto.request.auth.RegisterRequest;
import javohir.test.nextpizzafront.dto.response.UserResponse;
import javohir.test.nextpizzafront.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserClient userClient;
    private final JwtUtil jwtUtil;

    /**
     * Profil sahifasi
     */
    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request)  {
        // Navbar attributes
        addNavbarAttributes(model, request);

        try {
            // User ma'lumotlarini backend dan olish
            Long userId = jwtUtil.getUserIdFromRequest(request);
            UserResponse user = userClient.getProfile(userId);
            model.addAttribute("user", user);
            model.addAttribute("userUpdateRequest", new RegisterRequest());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("user", new UserResponse()); // MUHIM
            model.addAttribute("userUpdateRequest", new RegisterRequest());
            model.addAttribute("error", "Profilni yuklab bo'lmadi");
        }

        return "profile";
    }

    /**
     * Profilni yangilash
     */
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute RegisterRequest request,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest httpRequest) {
        try {
            // User ID ni olish (JWT dan yoki session dan)
            // Hozircha hardcoded, keyinchalik JWT parse qilish kerak
            Long userId = 1L; // TODO: JWT dan olish

            userClient.updateUser(userId, request);

            redirectAttributes.addFlashAttribute("success", "Profil muvaffaqiyatli yangilandi!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Profilni yangilashda xatolik: " + e.getMessage());
        }

        return "redirect:/profile";
    }
}
