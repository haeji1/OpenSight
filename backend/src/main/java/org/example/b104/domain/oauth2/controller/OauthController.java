package org.example.b104.domain.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.response.SocialLoginResponse;
import org.example.b104.domain.oauth2.sevice.OauthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class  OauthController {

    private final OauthService oauthService;

    @GetMapping("/api/login/oauth2/code/{provider}")
    public String socialLogin(@PathVariable String provider, @RequestParam("code") String code) {
        System.out.println("provider : " + provider + ", code : " + code);
        SocialLoginResponse loginResponse = oauthService.socialLogin(provider, code);
        System.out.println("longinResponse 토큰 : "+loginResponse.getJwtToken());
        String redirectUrl = "https://j10b104.p.ssafy.io/login/social?accessToken=" + loginResponse.getJwtToken() + "&refreshToken=" + loginResponse.getRefreshToken();
        return "redirect:" + redirectUrl;
    }
}
