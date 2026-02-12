package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.DrinksClient;
import javohir.test.nextpizzafront.dto.response.DrinkResponse;
import javohir.test.nextpizzafront.enums.DrinkType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/drinks")
@RequiredArgsConstructor
public class DrinksViewController extends BaseController {

    private final DrinksClient drinksClient;

    /**
     * Barcha ichimliklar
     */
    @GetMapping
    public String getAllDrinks(Model model, HttpServletRequest request) {
        // Navbar attributes
        addNavbarAttributes(model, request);

        // Drinks
        try {
            List<DrinkResponse> drinks = drinksClient.getAllDrinks();
            model.addAttribute("drinks", drinks);
        } catch (Exception e) {
            model.addAttribute("drinks", List.of());
            model.addAttribute("error", "Ichimliklarni yuklab bo'lmadi");
        }

        return "drinks";
    }

    /**
     * Type bo'yicha ichimliklar
     */
    @GetMapping("/type/{type}")
    public String getDrinksByType(@PathVariable DrinkType type,
                                  Model model,
                                  HttpServletRequest request) {
        // Navbar attributes
        addNavbarAttributes(model, request);

        // Drinks by type
        try {
            List<DrinkResponse> drinks = drinksClient.getDrinksByType(type);
            model.addAttribute("drinks", drinks);
            model.addAttribute("selectedType", type);
        } catch (Exception e) {
            model.addAttribute("drinks", List.of());
            model.addAttribute("error", "Ichimliklarni yuklab bo'lmadi");
        }

        return "drinks";
    }
}
