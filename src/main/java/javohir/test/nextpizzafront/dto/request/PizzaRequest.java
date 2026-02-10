package javohir.test.nextpizzafront.dto.request;


import javohir.test.nextpizzafront.enomerator.PizzaType;
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
