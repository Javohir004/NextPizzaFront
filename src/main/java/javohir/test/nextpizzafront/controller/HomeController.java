package javohir.test.nextpizzafront.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import javohir.test.nextpizzafront.client.PizzaClient;
import javohir.test.nextpizzafront.dto.response.PizzaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController extends BaseController {

    private final PizzaClient pizzaClient;

    @Value("${api.base-url}")
    private String baseUrl;

    @GetMapping("/")
    public String home(Model model , HttpServletRequest request) {
        addNavbarAttributes(model, request);
        try {
            List<PizzaResponse> pizzas = pizzaClient.getAllPizzas();
            int limit = Math.min(6, pizzas.size());
            model.addAttribute("pizzas", pizzas.subList(0, limit));

        } catch (Exception e) {
            // Agar backend ishlamasa, bo'sh list
            model.addAttribute("pizzas", List.of());
        }
        return "home";
    }


}