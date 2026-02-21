package javohir.test.nextpizzafront.dto.response.order;

import javohir.test.nextpizzafront.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String userFullName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private Integer totalItems;
    private String deliveryAddress;
    private List<OrderItemResponse> orderItems;

}
