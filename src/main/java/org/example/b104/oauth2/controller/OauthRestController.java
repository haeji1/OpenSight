package org.example.b104.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.oauth2.response.LoginResponse;
import org.example.b104.oauth2.sevice.OauthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthRestController {

    private final OauthService oauthService;


    @GetMapping("/login/oauth2/code/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam("code") String code) {
        System.out.println("provider : " + provider + ", code : " + code);
        LoginResponse loginResponse = oauthService.login(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
