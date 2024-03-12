package org.example.b104.domain.user.service;

import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.user.controller.response.LoginResponse;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.domain.user.service.command.LoginCommand;
import org.example.b104.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = false)
    public LoginResponse Login(LoginCommand command) {

        // 입력 유효성 검사
        if (command.getUserEmail() == null || command.getUserPassword() == null) {
            throw new InvalidRequestStateException("이메일과 비밀번호는 필수 입력값입니다.");
        }

        User user = userRepository.findByEmail(command.getUserEmail());
        if (user == null || !user.getPassword().equals(command.getUserPassword())) {
            throw new EntityNotFoundException("로그인 실패");
        }

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .tokenType("Bearer")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }
}
