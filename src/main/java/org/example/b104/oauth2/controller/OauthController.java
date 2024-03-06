package org.example.b104.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.oauth2.response.LoginResponse;
import org.example.b104.oauth2.sevice.OauthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login/oauth2/code/{provider}")
    public String login(@PathVariable String provider, @RequestParam("code") String code) {
        System.out.println("provider : " + provider + ", code : " + code);
        LoginResponse loginResponse = oauthService.login(provider, code);
        System.out.println("longinResponse 토큰 : "+loginResponse.getJwtToken());
        String redirectUrl = "http://192.168.31.38:5173/main?accessToken=" + loginResponse.getJwtToken() + "&refreshToken=" + loginResponse.getRefreshToken();
        return "redirect:" + redirectUrl;
    }
}
