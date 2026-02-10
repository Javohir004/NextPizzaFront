package javohir.test.nextpizzafront.client;

import javohir.test.nextpizzafront.dto.request.auth.LoginRequest;
import javohir.test.nextpizzafront.dto.request.auth.RegisterRequest;
import javohir.test.nextpizzafront.dto.response.UserResponse;
import javohir.test.nextpizzafront.dto.response.auth.AuthenticationResponse;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${api.base-url}")
public interface AuthClient {

    @PostMapping("/auth/register")
    UserResponse register(@RequestBody RegisterRequest request);

    @PostMapping("/auth/login")
    AuthenticationResponse login(@RequestBody LoginRequest request);
}