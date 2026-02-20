package javohir.test.nextpizzafront.controller.admin;


import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.PizzaClient;
import javohir.test.nextpizzafront.controller.BaseController;
import javohir.test.nextpizzafront.dto.request.PizzaRequest;
import javohir.test.nextpizzafront.dto.response.PizzaResponse;
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
@RequestMapping("/admin/pizzas")
@RequiredArgsConstructor
public class AdminPizzaController extends BaseController {

    private final PizzaClient pizzaClient;

    /**
     * Barcha pitsalar
     */
    @GetMapping
    public String listPizzas(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            List<PizzaResponse> pizzas = pizzaClient.getAllPizzas();
            model.addAttribute("pizzas", pizzas);

        } catch (Exception e) {
            log.error("Error loading pizzas: {}", e.getMessage());
            model.addAttribute("error", "Pitsalarni yuklab bo'lmadi");
            model.addAttribute("pizzas", List.of());
        }

        return "admin/pizzas";
    }

    /**
     * Yangi pitsa - form
     */
    @GetMapping("/create")
    public String createForm(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);
        return "admin/pizza-form";
    }

    /**
     * Yangi pitsa - save
     */
    @PostMapping("/create")
    public String createPizza(@ModelAttribute PizzaRequest request,
                              @RequestParam(required = false) MultipartFile image,
                              RedirectAttributes redirectAttributes) {
        try {
            // TODO: Image upload qilish kerak
            // Hozircha image yo'q

            pizzaClient.createPizza(request);

            log.info("Pizza created: {}", request.getName());
            redirectAttributes.addFlashAttribute("success", "Pitsa qo'shildi!");

            return "redirect:/admin/pizzas";

        } catch (Exception e) {
            log.error("Error creating pizza: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
            return "redirect:/admin/pizzas/create";
        }
    }

    /**
     * Pitsani tahrirlash - form
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           Model model,
                           HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            PizzaResponse pizza = pizzaClient.getPizzaById(id);
            model.addAttribute("pizza", pizza);

        } catch (Exception e) {
            log.error("Error loading pizza: {}", e.getMessage());
            return "redirect:/admin/pizzas";
        }

        return "admin/pizza-form";
    }

    /**
     * Pitsani yangilash
     */
    @PostMapping("/update/{id}")
    public String updatePizza(@PathVariable Long id,
                              @ModelAttribute PizzaRequest request,
                              @RequestParam(required = false) MultipartFile image,
                              RedirectAttributes redirectAttributes) {
        try {
            // TODO: Image upload

            pizzaClient.updatePizza(id, request);

            log.info("Pizza updated: {}", id);
            redirectAttributes.addFlashAttribute("success", "Pitsa yangilandi!");

        } catch (Exception e) {
            log.error("Error updating pizza: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
        }

        return "redirect:/admin/pizzas";
    }

    /**
     * Pitsani o'chirish (soft delete)
     */
    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            pizzaClient.deletePizza(id);

            log.info("Pizza deleted: {}", id);
            redirectAttributes.addFlashAttribute("success", "Pitsa o'chirildi!");

        } catch (Exception e) {
            log.error("Error deleting pizza: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik: " + e.getMessage());
        }

        return "redirect:/admin/pizzas";
    }
}
