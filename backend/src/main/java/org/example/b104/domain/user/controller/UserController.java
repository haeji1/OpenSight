package org.example.b104.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.response.SocialLoginResponse;
import org.example.b104.domain.user.controller.request.LoginRequest;
import org.example.b104.domain.user.controller.response.LoginResponse;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        LoginResponse loginResponse = userService.Login(request.toLogin());
        return ResponseEntity.ok(ApiResponse.createSuccess(loginResponse));

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
            @RequestBody CreateUserRequest request
    ) {
        CreateUserResponse createUserResponse =  userService.createUser(request.toCreateUserCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(createUserResponse));
    }

    @PostMapping("/update-info")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUser(
            @RequestBody UpdateUserRequest request
    ) {
        UpdateUserResponse updateUserResponse = userService.updateUser(request.toUpdateUserCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(updateUserResponse));
    }


    @GetMapping("/find-pw")
    public ResponseEntity<ApiResponse<FindPasswordResponse>> findPassword(
            @RequestBody FindPasswordRequest request
    ) {
        FindPasswordResponse findPasswordResponse = userService.findPassword(request.toFindPasswordCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(findPasswordResponse));
    }

//    @PostMapping("update-pw")
//    public ResponseEntity<ApiResponse<UpdateUserResponse>> updatePassword(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        String userId = extractUserIdFromToken(token);
//
//        // update
//
//        return ResponseEntity.ok().build();
//    }
//
//    private String extractUserIdFromToken(String token) {
//        // 토큰 디코딩
//        Claims claims = Jwts.parser()
//                .setSigningKey("")
//                .parseClaimsJws(token.replace("Bearer ", "")) // "Bearer " 부분 제거
//                .getBody();
//        // Payload에서 userId 추출
//        return claims.get("userId", String.class);
//    }


    /*@PostMapping("/authtest")
    public ResponseEntity<String> authtest(@RequestHeader("Authorization") String token) {
        try{
            System.out.println("encrypted token : " + token);
            String decryptedToken = jwtTokenProvider.getPayload(token);
            System.out.println("decrypted token : " + decryptedToken);

            return ResponseEntity.ok(decryptedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("failed");
    }*/


    /*@PostMapping("/authtest")
    public ResponseEntity<String> authtest(@RequestHeader("Authorization") String token) {
        try{
            System.out.println("encrypted token : " + token);
            String decryptedToken = jwtTokenProvider.getPayload(token);
            System.out.println("decrypted token : " + decryptedToken);

            return ResponseEntity.ok(decryptedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("failed");
    }*/
}
