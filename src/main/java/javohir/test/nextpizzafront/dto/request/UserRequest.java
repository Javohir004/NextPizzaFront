package javohir.test.nextpizzafront.dto.request;

import javohir.test.nextpizzafront.enums.Role;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private String email;
    private LocalDate birthDate;
    private String phoneNumber;
    private BigDecimal balance;
    private String address;


}
