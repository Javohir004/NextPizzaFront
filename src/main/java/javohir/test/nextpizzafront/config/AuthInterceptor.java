package javohir.test.nextpizzafront.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();

        // Public pages
        if (isPublicPage(uri)) {
            return true;
        }

        // JWT cookie bormi?
        if (hasJwtCookie(request)) {
            return true;  // OK
        }

        // JWT yo'q - login ga redirect
        log.warn("Unauthorized access to: {}", uri);
        response.sendRedirect("/login?error=Tizimga kirish kerak");
        return false;
    }

    private boolean isPublicPage(String uri) {
        return uri.equals("/") ||
                uri.equals("/login") ||
                uri.equals("/register") ||
                uri.equals("/logout") ||
                uri.startsWith("/css") ||
                uri.startsWith("/js") ||
                uri.startsWith("/images") ||
                uri.startsWith("/error");
    }

    private boolean hasJwtCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName()) &&
                        cookie.getValue() != null &&
                        !cookie.getValue().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
