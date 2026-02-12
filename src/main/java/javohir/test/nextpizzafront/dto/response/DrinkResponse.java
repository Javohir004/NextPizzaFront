package javohir.test.nextpizzafront.dto.response;

import javohir.test.nextpizzafront.enums.DrinkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrinkResponse {
    private Long id;
    private String drinkName;
    private DrinkType drinkType;
    private Double volume;
    private BigDecimal price;
    private String imageUrl;
    private boolean active;
}
