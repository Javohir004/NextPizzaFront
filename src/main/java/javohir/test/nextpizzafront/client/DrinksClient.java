package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.response.DrinkResponse;
import javohir.test.nextpizzafront.enomerator.DrinkType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "drinks-service", url = "${api.base-url}")
public interface DrinksClient {

    @GetMapping("/drinks")
    List<DrinkResponse> getAllDrinks();

    @GetMapping("/drinks/type/{type}")
    List<DrinkResponse> getDrinksByType(@PathVariable DrinkType type);
}