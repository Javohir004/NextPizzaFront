package javohir.test.nextpizzafront.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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

        // Check JWT cookie
        if (hasValidToken(request)) {
            return true;
        }

        // Redirect to login
        response.sendRedirect("/login?error=Tizimga kirish kerak");
        return false;
    }

    private boolean isPublicPage(String uri) {
        return uri.equals("/") ||
                uri.equals("/login") ||
                uri.equals("/register") ||
                uri.startsWith("/css") ||
                uri.startsWith("/js") ||
                uri.startsWith("/images") ||
                uri.startsWith("/uploads");
    }

    private boolean hasValidToken(HttpServletRequest request) {
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
