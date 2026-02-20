package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.DrinkRequest;
import javohir.test.nextpizzafront.dto.response.DrinkResponse;
import javohir.test.nextpizzafront.enums.DrinkType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "drinks-service", url = "${api.base-url}")
public interface DrinksClient {

    @GetMapping("/drink/find-all")
    List<DrinkResponse> getAllDrinks();

    @GetMapping("/drink/get-by-type/{type}")
    List<DrinkResponse> getDrinksByType(@PathVariable DrinkType type);

    void deleteDrink(Long id);

    void updateDrink(Long id, DrinkRequest request);

    DrinkResponse getDrinkById(Long id);

    void createDrink(DrinkRequest request);
}