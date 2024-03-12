package org.example.b104.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.response.SocialLoginResponse;
import org.example.b104.domain.user.controller.request.LoginRequest;
import org.example.b104.domain.user.controller.response.LoginResponse;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    final UserService userService;
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.createSuccess(userService.Login(request.toLogin())));
    }


}
