package javohir.test.nextpizzafront.client;



import javohir.test.nextpizzafront.dto.request.auth.RegisterRequest;
import javohir.test.nextpizzafront.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "user-service", url = "${api.base-url}")
public interface UserClient {

    @GetMapping("/user/find-by-id/{userId}")
    UserResponse getProfile(@PathVariable("userId") Long userId);

    @GetMapping("/user/my-balance")
    Double getBalance();

    @PutMapping("/user/update/{id}")
    UserResponse updateUser(@PathVariable Long id, @RequestBody RegisterRequest request);

    /**
     * Joriy userni olish (JWT dan)
     */
    @GetMapping("/user/me")
    UserResponse getCurrentUser();

}
