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
    public MakeManagerKeyResponse makeManagerKey(String managerId) {
        MakeManagerKeyResponse response = new MakeManagerKeyResponse();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://finapi.p.ssafy.io/ssafy/api/v1/edu/app/issuedApiKey"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"managerId\": \"" + managerId + "\"}"))
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String result = jsonNode.get("result").asText();
            if (result.equals("success")){
                return MakeManagerKeyResponse.builder()
                        .result("success")
                        .managerId(managerId)
                        .apiKey(responseBody)
                        .build();
            }
            else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public MakeManagerKeyResponse remakeManagerKey(String managerId) {
        MakeManagerKeyResponse response = new MakeManagerKeyResponse();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://finapi.p.ssafy.io/ssafy/api/v1/edu/app/reIssuedApiKey"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"managerId\": \"" + managerId + "\"}"))
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String responseCode = jsonNode.get("responseCode").asText();
            if (!responseCode.isBlank()) {
                return MakeManagerKeyResponse.builder()
                        .result("success")
                        .managerId(managerId)
                        .apiKey(responseBody)
                        .build();
            } else {
                String responseMessage = jsonNode.get("responseMessage").asText();
                System.out.println("[ERROR] " + responseMessage);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
