package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.DrinkRequest;
import javohir.test.nextpizzafront.dto.response.DrinkResponse;
import javohir.test.nextpizzafront.enums.DrinkType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "drinks-service", url = "${api.base-url}")
public interface DrinksClient {

    @GetMapping("/drink/find-all")
    List<DrinkResponse> getAllDrinks();

    @GetMapping("/drink/get-by-type/{type}")
    List<DrinkResponse> getDrinksByType(@PathVariable DrinkType type);


    // Admin endpoints qo'shish
    @PostMapping(value = "/drink/create-drink" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DrinkResponse createDrink(@RequestPart("drinkRequest") DrinkRequest request,
                              @RequestPart("drinkType") String drinkType,
                              @RequestPart("image") MultipartFile image);

    @PostMapping( value = "/drink/update-drink/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DrinkResponse updateDrink(@PathVariable Long id,
                              @RequestPart("drinkRequest") DrinkRequest request,
                              @RequestPart("drinkType") String drinkType,
                              @RequestPart("image")  MultipartFile image);

    @DeleteMapping("/drink/delete-drink/{id}")
    void deleteDrink(@PathVariable Long id);

    @GetMapping("/drink/findby-id/{id}")
    DrinkResponse getDrinkById(@PathVariable Long id);

    @GetMapping("drink/find-all-for-admin")
    List<DrinkResponse> getAllDrinksForAdmin();
}