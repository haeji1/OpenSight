package org.example.b104.domain.account.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.b104.domain.account.controller.response.MakeManagerKeyResponse;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AccountService {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    /*
  "managerId" : "JinhoRyu.dev@gmail.com",
  "apiKey" : "64e3fbfadb5d4d73baf1f7523fc5887e"
     */

    AccountService() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public HttpResponse<String> SendHttpRequest(String url, String method, String body) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public MakeManagerKeyResponse makeManagerKey(String managerId) {
        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/edu/app/issuedApiKey", "POST", "{\"managerId\": \"" + managerId + "\"}");

        if (httpResponse == null) return null;
        if (httpResponse.statusCode() != 200) return null;

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String resultManagerId = jsonNode.get("managerId").asText();
            String resultApiKey = jsonNode.get("apiKey").asText();
            return MakeManagerKeyResponse.builder()
                    .result("success")
                    .managerId(resultManagerId)
                    .apiKey(resultApiKey)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public MakeManagerKeyResponse remakeManagerKey(String managerId) {
        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/edu/app/reIssuedApiKey", "POST", "{\"managerId\": \"" + managerId + "\"}");

        if (httpResponse == null) return null;
        if (httpResponse.statusCode() != 200) return null;

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String resultManagerId = jsonNode.get("managerId").asText();
            String resultApiKey = jsonNode.get("apiKey").asText();
            return MakeManagerKeyResponse.builder()
                    .result("success")
                    .managerId(resultManagerId)
                    .apiKey(resultApiKey)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
