package javohir.test.nextpizzafront.dto.request.order;

import jakarta.validation.constraints.NotNull;
import javohir.test.nextpizzafront.enums.OrderStatus;
import lombok.Data;


@Data
public class UpdateOrderStatusRequest {

    @NotNull(message = "Status kiritilishi shart")
    private OrderStatus status;
}