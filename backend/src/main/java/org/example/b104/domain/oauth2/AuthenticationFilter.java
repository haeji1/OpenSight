package org.example.b104.domain.oauth2;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class AuthenticationFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    // 인증 없이 접근 허용되는 페이지
    private static final String[] whitelist = {"/public"};

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 요청한 페이지의 경로
        String requestURI = httpServletRequest.getRequestURI();

        // whitelist에 포함된 경로인지 확인
        for (String path : whitelist) {
            if (requestURI.startsWith(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }


        // 헤더에서 토큰 추출
        String token = extractTokenFromHeader(httpServletRequest);

        if (token == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 토큰이 유효한 경우
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

}
