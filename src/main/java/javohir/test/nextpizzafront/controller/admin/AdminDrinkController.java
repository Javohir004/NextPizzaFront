package javohir.test.nextpizzafront.controller.admin;



import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.DrinksClient;
import javohir.test.nextpizzafront.controller.BaseController;
import javohir.test.nextpizzafront.dto.request.DrinkRequest;
import javohir.test.nextpizzafront.dto.response.DrinkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/drinks")
@RequiredArgsConstructor
public class AdminDrinkController extends BaseController {

    private final DrinksClient drinksClient;

    /**
     * Barcha ichimliklar
     */
    @GetMapping
    public String listDrinks(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            List<DrinkResponse> drinks = drinksClient.getAllDrinksForAdmin();
            model.addAttribute("drinks", drinks);

        } catch (Exception e) {
            log.error("Error loading drinks: {}", e.getMessage());
            model.addAttribute("error", "Ichimliklarni yuklab bo'lmadi");
            model.addAttribute("drinks", List.of());
        }

        return "admin/drinks";
    }

    /**
     * Yangi ichimlik - form
     */
    @GetMapping("/create")
    public String createForm(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);
        return "admin/drink-form";
    }

    /**
     * Yangi ichimlik - save
     */
    @PostMapping("/create")
    public String createDrink(@ModelAttribute DrinkRequest request,
                              @RequestParam(required = false) MultipartFile image,
                              RedirectAttributes redirectAttributes) {
        try {
            // TODO: Image upload

            drinksClient.createDrink(request);

            log.info("Drink created: {}", request.getDrinkName());
            redirectAttributes.addFlashAttribute("success", "Ichimlik qo'shildi!");

            return "redirect:/admin/drinks";

        } catch (Exception e) {
            log.error("Error creating drink: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
            return "redirect:/admin/drinks/create";
        }
    }

    /**
     * Ichimlikni tahrirlash - form
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           Model model,
                           HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            DrinkResponse drink = drinksClient.getDrinkById(id);
            model.addAttribute("drink", drink);

        } catch (Exception e) {
            log.error("Error loading drink: {}", e.getMessage());
            return "redirect:/admin/drinks";
        }

        return "admin/drink-form";
    }

    /**
     * Ichimlikni yangilash
     */
    @PostMapping("/update/{id}")
    public String updateDrink(@PathVariable Long id,
                              @ModelAttribute DrinkRequest request,
                              @RequestParam(required = false) MultipartFile image,
                              RedirectAttributes redirectAttributes) {
        try {
            // TODO: Image upload

            drinksClient.updateDrink(id, request);

            log.info("Drink updated: {}", id);
            redirectAttributes.addFlashAttribute("success", "Ichimlik yangilandi!");

        } catch (Exception e) {
            log.error("Error updating drink: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
        }

        return "redirect:/admin/drinks";
    }

    /**
     * Ichimlikni o'chirish
     */
    @PostMapping("/delete/{id}")
    public String deleteDrink(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            drinksClient.deleteDrink(id);

            log.info("Drink deleted: {}", id);
            redirectAttributes.addFlashAttribute("success", "Ichimlik o'chirildi!");

        } catch (Exception e) {
            log.error("Error deleting drink: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
        }

        return "redirect:/admin/drinks";
    }
}
