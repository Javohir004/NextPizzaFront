package javohir.test.nextpizzafront.controller;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import javohir.test.nextpizzafront.client.CartClient;
import javohir.test.nextpizzafront.dto.request.cart.AddDrinkToCartRequest;
import javohir.test.nextpizzafront.dto.request.cart.AddPizzaToCartRequest;
import javohir.test.nextpizzafront.dto.request.cart.UpdateCartItemRequest;
import javohir.test.nextpizzafront.dto.response.cart.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {

    private final CartClient cartClient;

    /**
     * Savatni ko'rish
     */
    @GetMapping
    public String viewCart(Model model, HttpServletRequest request) {
        addNavbarAttributes(model, request);

        try {
            CartResponse cart = cartClient.getCart();
            model.addAttribute("cart", cart);
            System.out.println(cart.getTotalItems());

        } catch (FeignException e) {
            log.error("Error fetching cart: {}", e.getMessage());
            model.addAttribute("error", "Savatni yuklab bo'lmadi");
        }

        return "user/cart";
    }

    /**
     * Pizza qo'shish
     */
    @PostMapping("/add-pizza")
    public String addPizza(@RequestParam Long pizzaId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            AddPizzaToCartRequest request = new AddPizzaToCartRequest(pizzaId, quantity);
            cartClient.addPizza(request);

            log.info("Pizza added to cart: pizzaId={}, quantity={}", pizzaId, quantity);
            redirectAttributes.addFlashAttribute("success", "Pizza savatga qo'shildi!");

        } catch (FeignException e) {
            log.error("Error adding pizza to cart: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik yuz berdi");
        }

        // Qayerdan kelgan bo'lsa, o'sha sahifaga qaytish
        return "redirect:" + (referer != null ? referer : "user/pizzas");
    }

    /**
     * Drink qo'shish
     */
    @PostMapping("/add-drink")
    public String addDrink(@RequestParam Long drinkId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            AddDrinkToCartRequest request = new AddDrinkToCartRequest(drinkId, quantity);
            cartClient.addDrink(request);

            log.info("Drink added to cart: drinkId={}, quantity={}", drinkId, quantity);
            redirectAttributes.addFlashAttribute("success", "Ichimlik savatga qo'shildi!");

        } catch (FeignException e) {
            log.error("Error adding drink to cart: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik yuz berdi");
        }

        return "redirect:" + (referer != null ? referer : "user/drinks");
    }

    /**
     * Quantity yangilash (AJAX dan)
     */
    @PostMapping("/update/{itemId}")
    @ResponseBody
    public CartResponse updateQuantity(@PathVariable Long itemId,
                                       @RequestParam Integer quantity) {
        try {
            UpdateCartItemRequest request = new UpdateCartItemRequest(quantity);
            return cartClient.updateQuantity(itemId, request);

        } catch (FeignException e) {
            log.error("Error updating cart item: {}", e.getMessage());
            throw new RuntimeException("Xatolik yuz berdi");
        }
    }

    /**
     * Item o'chirish
     */
    @PostMapping("/remove/{itemId}")
    public String removeItem(@PathVariable Long itemId,
                             RedirectAttributes redirectAttributes) {
        try {
            cartClient.removeItem(itemId);

            log.info("Cart item removed: itemId={}", itemId);
            redirectAttributes.addFlashAttribute("success", "Mahsulot o'chirildi");

        } catch (FeignException e) {
            log.error("Error removing cart item: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik yuz berdi");
        }

        return "redirect:/cart";
    }

    /**
     * Savatni tozalash
     */
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        try {
            cartClient.clearCart();

            log.info("Cart cleared");
            redirectAttributes.addFlashAttribute("success", "Savat tozalandi");

        } catch (FeignException e) {
            log.error("Error clearing cart: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Xatolik yuz berdi");
        }

        return "redirect:/cart";
    }
}

