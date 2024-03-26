package org.example.b104.domain.user.controller;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.user.controller.request.*;
import org.example.b104.domain.user.controller.response.*;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;

    @Value("${jwt.token.secret-key}")
    private String secretKey;
    
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

    @PostMapping("update-pw")
    public ResponseEntity<ApiResponse<UpdatePasswordResponse>> updatePassword(
            HttpServletRequest request,
            @RequestBody UpdatePasswordRequest passwordRequest
    ) {

        String token = request.getHeader("Authorization");
        System.out.println("=====token====="+token);
        String user = extractUserIdFromToken(token);
        System.out.println("===user===="+user);
        Long userId = Long.parseLong(user);
        System.out.println("=====userId====="+userId);

        // 비밀번호 수정
        UpdatePasswordResponse updatePasswordResponse = userService.updatePassword(passwordRequest.toUpdatePasswordCommand(), userId);
        return ResponseEntity.ok(ApiResponse.createSuccess(updatePasswordResponse));
    }

    @GetMapping("/find-email")
    public ResponseEntity<ApiResponse<FindEmailResponse>> findEmail(
            @RequestBody FindEmailRequest request
    ) {
        FindEmailResponse findEmailResponse = userService.findEmail(request.toFindEmail());
        return ResponseEntity.ok(ApiResponse.createSuccess(findEmailResponse));
    }

    @DeleteMapping("/withdrawl")
    public ResponseEntity<ApiResponse<DeleteUserResponse>> deleteUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        DeleteUserResponse deleteUserResponse = userService.deleteUser(email,password);
        return ResponseEntity.ok(ApiResponse.createSuccess(deleteUserResponse));
    }


    private String extractUserIdFromToken(String token) {
        if (token != null) {
            System.out.println("not null");;
            String jwttoken =  token.substring(7); // "Bearer "의 길이는 7입니다.
            System.out.println("================jwtToken======="+jwttoken);
//            Claims claims = Jwts.parser().parseClaimsJws(jwttoken).getBody();
//            System.out.println("====claims===="+claims);
//            return (String) claims.get("sub");
            try {
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwttoken).getBody();
                System.out.println("====claims====" + claims);
                return (String) claims.get("sub");
            } catch (ExpiredJwtException ex) {
                // 토큰이 만료된 경우에 대한 처리
                ex.printStackTrace(); // 또는 로그 기록
            } catch (SignatureException | MalformedJwtException ex) {
                // 서명이 올바르지 않거나 JWT 형식이 잘못된 경우에 대한 처리
                ex.printStackTrace(); // 또는 로그 기록
            } catch (Exception ex) {
                // 그 외 다른 예외에 대한 처리
                ex.printStackTrace(); // 또는 로그 기록
            }
        }
        return null;
    }

    // test


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
