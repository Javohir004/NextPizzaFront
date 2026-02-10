package javohir.test.nextpizzafront.controller;

import org.springframework.ui.Model;
import javohir.test.nextpizzafront.client.PizzaClient;
import javohir.test.nextpizzafront.dto.response.PizzaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PizzaClient pizzaClient;

    @GetMapping("/")
    public String home(Model model) {
        List<PizzaResponse> pizzas = pizzaClient.getAllPizzas();
        model.addAttribute("pizzas", pizzas);
        return "home"; // home.html
    }
}