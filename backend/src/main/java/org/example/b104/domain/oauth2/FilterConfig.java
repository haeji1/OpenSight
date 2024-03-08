package org.example.b104.domain.oauth2;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public FilterConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter(jwtTokenProvider)); // 필터 인스턴스를 등록
        registrationBean.addUrlPatterns("/*"); // 필터를 모든 URL에 적용
        registrationBean.setOrder(1); // 필터 순서 설정
        return registrationBean;
    }
}
