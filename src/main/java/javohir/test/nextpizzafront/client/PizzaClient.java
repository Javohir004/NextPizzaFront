package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.PizzaRequest;
import javohir.test.nextpizzafront.dto.response.PizzaResponse;
import javohir.test.nextpizzafront.enums.PizzaType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "pizza-service", url = "${api.base-url}")
public interface PizzaClient {

    @GetMapping("/pizza")
    List<PizzaResponse> getAllPizzas();

    @GetMapping("/pizza/grouped")
    Map<PizzaType, List<PizzaResponse>> getPizzasGrouped();

    @GetMapping("/pizza/type/{type}")
    List<PizzaResponse> getPizzasByType(@PathVariable PizzaType type);


    // Admin endpoints qo'shish
    @PostMapping("/pizza/create-pizza")
    PizzaResponse createPizza(@RequestBody PizzaRequest request);

    @PutMapping("/pizza/update/{id}")
    PizzaResponse updatePizza(@PathVariable Long id, @RequestBody PizzaRequest request);

    @DeleteMapping("/pizza/delete/{id}")
    void deletePizza(@PathVariable Long id);

    @GetMapping("/pizza/{id}")
    PizzaResponse getPizzaById(@PathVariable Long id);

}
