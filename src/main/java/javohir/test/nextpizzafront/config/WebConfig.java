package javohir.test.nextpizzafront.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. Auth check (JWT bormi?)
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/register", "/logout",
                        "/css/**", "/js/**", "/images/**", "/error", "/403");

        // 2. Role check (Admin/Owner access)
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/admin/**", "/owner/**");
    }
}
