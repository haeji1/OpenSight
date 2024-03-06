//package org.example.b104.global.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {//패스워드 암호화를 위한 빈
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests((authorizeRequests)->
//                        authorizeRequests.anyRequest().permitAll()
//                );// 모든 요청에 대해 인증 필요 없음
//        return http.build();
//
//        /*return http
//                .csrf().disable()
//                .headers(headers -> headers.frameOptions().sameOrigin())	// H2 콘솔 사용을 위한 설정
//                .authorizeHttpRequests(requests ->
//                        requests.requestMatchers("/", "/swagger-ui/**", "/v3/**").permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 콘솔 접속은 모두에게 허용
//                                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )	// 세션을 사용하지 않으므로 STATELESS 설정
//                .build();*/
//    }
//}
