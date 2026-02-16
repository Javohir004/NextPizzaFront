package javohir.test.nextpizzafront.dto.request.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddDrinkToCartRequest {
    @NotNull
    public Long drinkId;

    @NotNull
    @Min(1)
    public Integer quantity;
}
