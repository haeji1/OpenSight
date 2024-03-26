package org.example.b104.domain.user.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.user.controller.request.*;
import org.example.b104.domain.user.controller.response.*;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;

    @Autowired
    final JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/face-login")
    public ResponseEntity<ApiResponse<LoginResponse>> faceLogin(
            @ModelAttribute FaceLoginRequest request
    ) {
        LoginResponse loginResponse = userService.faceLogin(request.toFaceLoginCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(loginResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
            @ModelAttribute CreateUserImageRequest request
            //@RequestBody CreateUserRequest request,
            //@RequestParam(name = "profileImage", required = false)  MultipartFile profileImage
    ) {


        CreateUserResponse createUserResponse;
        if (request.getProfileImage() != null){
            createUserResponse = userService.createUserWithProfileImage(request.toCreateUserCommand(), request.getProfileImage());
        }else {
            createUserResponse = userService.createUser(request.toCreateUserCommand());
        }
        return ResponseEntity.ok(ApiResponse.createSuccess(createUserResponse));
    }




    @PostMapping("/update-info")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUser(
            @RequestBody UpdateUserRequest request
    ) {
        UpdateUserResponse updateUserResponse = userService.updateUser(request.toUpdateUserCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(updateUserResponse));
    }


    @PostMapping("/update-face")
    public ResponseEntity<ApiResponse<UpdateFaceResponse>>updateFace(
            @RequestHeader("Authorization") String token,
            @ModelAttribute UpdateFaceRequest request
    ){
        String decryptedToken = jwtTokenProvider.getPayload(token);
        Long userId = Long.parseLong(decryptedToken);

        UpdateFaceResponse updateFaceResponse = userService.updateFace(request.toUpdateFaceCommand(),userId);
        return ResponseEntity.ok(ApiResponse.createSuccess(updateFaceResponse));
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
            @RequestHeader("Authorization") String token,
            @RequestBody UpdatePasswordRequest passwordRequest
    ) {
        String decryptedToken = jwtTokenProvider.getPayload(token);
        Long userId = Long.parseLong(decryptedToken);

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


    private String extractUserIdFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwttoken =  token.substring(7); // "Bearer "의 길이는 7입니다.
            Claims claims = Jwts.parser().parseClaimsJws(jwttoken).getBody();
            return (String) claims.get("sub");
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
