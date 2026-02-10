package javohir.test.nextpizzafront.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrderRequest {
    @NotBlank(message = "Yetkazish manzili kiritilishi shart")
    private String deliveryAddress;

}
