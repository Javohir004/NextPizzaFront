package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.response.PizzaResponse;
import javohir.test.nextpizzafront.enomerator.PizzaType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "pizza-service", url = "${api.base-url}")
public interface PizzaClient {

    @GetMapping("/api/pizza")
    List<PizzaResponse> getAllPizzas();

    @GetMapping("/pizza/grouped")
    Map<PizzaType, List<PizzaResponse>> getPizzasGrouped();

    @GetMapping("/pizza/type/{type}")
    List<PizzaResponse> getPizzasByType(@PathVariable PizzaType type);
}
