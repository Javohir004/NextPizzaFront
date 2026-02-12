package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.PizzaClient;
import javohir.test.nextpizzafront.dto.response.PizzaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

        return "pizzas";
    }
}
