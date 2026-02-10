package javohir.test.nextpizzafront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "javohir.test.nextpizzafront.client")
public class NextPizzaFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextPizzaFrontApplication.class, args);
    }

}
