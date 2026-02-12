package javohir.test.nextpizzafront.dto.response;

import javohir.test.nextpizzafront.enums.Role;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private LocalDate birthDate;
    private String phoneNumber;
    private BigDecimal balance;
    private String address;
    private boolean enabled;
    private boolean isActive;
    private LocalDateTime createdDate;
}
