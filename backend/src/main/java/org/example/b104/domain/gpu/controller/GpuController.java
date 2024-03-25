package org.example.b104.domain.gpu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class GpuController {

    private final RestTemplate restTemplate;

    @GetMapping("/connect-gpu")
    public String connectGpuServer() {
        String gpuServerUrl = "http://127.0.0.1:7979";
        String Endpoint = "";

        ResponseEntity<String> response = restTemplate.getForEntity(gpuServerUrl + Endpoint, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return "연결실패";
        }
    }
}
