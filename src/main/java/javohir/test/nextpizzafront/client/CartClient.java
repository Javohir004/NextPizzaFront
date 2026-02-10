package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.cart.AddDrinkToCartRequest;
import javohir.test.nextpizzafront.dto.request.cart.AddPizzaToCartRequest;
import javohir.test.nextpizzafront.dto.request.cart.UpdateCartItemRequest;
import javohir.test.nextpizzafront.dto.response.cart.CartResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cart-service", url = "${api.base-url}")
public interface CartClient {

    @GetMapping("/cart")
    CartResponse getCart();

    @PostMapping("/cart/add-pizza")
    CartResponse addPizza(@RequestBody AddPizzaToCartRequest request);

    @PostMapping("/cart/add-drink")
    CartResponse addDrink(@RequestBody AddDrinkToCartRequest request);

    @PutMapping("/cart/item/{itemId}")
    CartResponse updateQuantity(@PathVariable Long itemId, @RequestBody UpdateCartItemRequest request);

    @DeleteMapping("/cart/item/{itemId}")
    CartResponse removeItem(@PathVariable Long itemId);

    @DeleteMapping("/cart/clear")
    void clearCart();

}
