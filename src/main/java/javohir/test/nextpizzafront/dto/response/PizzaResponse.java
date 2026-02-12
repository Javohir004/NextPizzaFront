package javohir.test.nextpizzafront.dto.response;

import javohir.test.nextpizzafront.enums.PizzaType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PizzaResponse {

    private Long foodId;

    private String name;

    private String description;

    private PizzaType pizzaType;

    private BigDecimal price;

    private String imageUrl;

    private LocalDateTime createDate;
}
