package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.UserClient;
import javohir.test.nextpizzafront.dto.request.auth.RegisterRequest;
import javohir.test.nextpizzafront.dto.response.UserResponse;
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

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            UserResponse user = userClient.getCurrentUser();
            model.addAttribute("user", user);
            model.addAttribute("userUpdateRequest", new RegisterRequest());

        } catch (Exception e) {
            model.addAttribute("error", "Profilni yuklab bo'lmadi");
        }

        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute RegisterRequest request,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest httpRequest) {
        try {
            // User ID ni backend dan olish
            UserResponse currentUser = userClient.getCurrentUser();

            // Update
            userClient.updateUser(currentUser.getId(), request);

            redirectAttributes.addFlashAttribute("success", "Profil yangilandi!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
        }

        return "redirect:/user/profile";
    }
}
