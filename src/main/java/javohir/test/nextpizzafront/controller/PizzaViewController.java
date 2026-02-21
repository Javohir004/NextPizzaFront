package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.PizzaClient;
import javohir.test.nextpizzafront.dto.response.PizzaResponse;
import javohir.test.nextpizzafront.enums.PizzaType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
@RequiredArgsConstructor
public class PizzaViewController extends BaseController {  // ← extends BaseController

    private final PizzaClient pizzaClient;

    @GetMapping
    public String getAllPizzas(Model model, HttpServletRequest request) {
        // Navbar attributes
        addNavbarAttributes(model, request);

        // Pizzas
        try {
            List<PizzaResponse> pizzas = pizzaClient.getAllPizzas();
            model.addAttribute("pizzas", pizzas);
        } catch (Exception e) {
            model.addAttribute("pizzas", List.of());
        }

        return "user/pizzas";
    }

    @GetMapping("/type/{pizzaType}")
    public String getPizzaByType(@PathVariable PizzaType pizzaType,
                                 Model model,
                                 HttpServletRequest request) {
        addNavbarAttributes(model, request);
        try{
        List<PizzaResponse> pizzas = pizzaClient.getPizzasByType(pizzaType);
        model.addAttribute("pizzas", pizzas);
        model.addAttribute("selectedType", pizzaType); // Tanlangan type
        }catch(Exception e){
            model.addAttribute("pizzas", List.of());
        }
        return "user/pizzas";
    }

}
