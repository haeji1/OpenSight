package org.example.b104.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.response.SocialLoginResponse;
import org.example.b104.domain.user.controller.request.CreateUserRequest;
import org.example.b104.domain.user.controller.request.LoginRequest;
import org.example.b104.domain.user.controller.response.CreateUserResponse;
import org.example.b104.domain.user.controller.response.LoginResponse;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
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
        LoginResponse loginResponse = userService.Login(request.toLogin());
        return ResponseEntity.ok(ApiResponse.createSuccess(loginResponse));

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
            @RequestBody CreateUserRequest request
    ) {
        CreateUserResponse createUserResponse =  userService.createUser(request.toCreateUserCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(createUserResponse));
    }

}
