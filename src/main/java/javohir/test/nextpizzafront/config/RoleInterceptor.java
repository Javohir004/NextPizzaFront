package javohir.test.nextpizzafront.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javohir.test.nextpizzafront.client.UserClient;
import javohir.test.nextpizzafront.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RoleInterceptor implements HandlerInterceptor {

    private final UserClient userClient;

    // @Lazy qo'shish - circular dependency fix
    public RoleInterceptor(@Lazy UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();

        // Admin pages - ADMIN yoki OWNER
        if (uri.startsWith("/admin")) {
            return checkRole(request, response, uri, "ADMIN", "OWNER");
        }

        // Owner pages - faqat OWNER
        if (uri.startsWith("/owner")) {
            return checkRole(request, response, uri, "OWNER");
        }

        // Default - ruxsat
        return true;
    }

    /**
     * Role tekshirish
     */
    private boolean checkRole(HttpServletRequest request,
                              HttpServletResponse response,
                              String uri,
                              String... allowedRoles) throws Exception {
        try {
            UserResponse user = userClient.getCurrentUser();

            // Role check
            for (String role : allowedRoles) {
                if (user.getRole().name().equals(role)) {
                    return true;  // Ruxsat ✅
                }
            }

            // Role mos kelmadi - 403
            log.warn("Access denied: {} tried to access {} (Role: {})",
                    user.getEmail(), uri, user.getRole());

            response.sendRedirect("/403");
            return false;

        } catch (Exception e) {
            // JWT invalid - login
            response.sendRedirect("/login?error=Tizimga kirish kerak");
            return false;
        }
    }
}