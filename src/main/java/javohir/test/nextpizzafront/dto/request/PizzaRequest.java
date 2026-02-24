package javohir.test.nextpizzafront.dto.request;


import jakarta.validation.constraints.NotNull;
import javohir.test.nextpizzafront.enums.PizzaType;
import lombok.*;


import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PizzaRequest {

    private String name;

    private String description;

    private PizzaType pizzaType;

    private BigDecimal price;

    private String imageUrl;
}
