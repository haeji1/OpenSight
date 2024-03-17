package org.example.b104.domain.account.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.b104.domain.account.controller.response.MakeManagerKeyResponse;
import org.example.b104.domain.account.controller.response.RegisterAccountMemberResponse;
import org.example.b104.domain.account.controller.response.SearchAccountMemberResponse;
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

    /**
     *
     * @param apiKey
     * @param userId : 이메일 형식
     * @return
     */
    public RegisterAccountMemberResponse registerAccountMember(String apiKey, String userId) {
        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/member",
                "POST",
                "{\"apiKey\": \"" + apiKey + "\", \"userId\": \"" + userId + "\"}");

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return RegisterAccountMemberResponse.builder()
                    .result("error")
                    .build();

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String result = jsonNode.get("code").asText();
            String resultUserId = jsonNode.get("payload").get("userId").asText();
            String userName = jsonNode.get("payload").get("userName").asText();
            String institutionCode = jsonNode.get("payload").get("institutionCode").asText();
            String userKey = jsonNode.get("payload").get("userKey").asText();
            String created = jsonNode.get("payload").get("created").asText();
            String modified = jsonNode.get("payload").get("modified").asText();
            String now = jsonNode.get("now").asText();

            return RegisterAccountMemberResponse.builder()
                    .result(result)
                    .userId(resultUserId)
                    .userName(userName)
                    .institutionCode(institutionCode)
                    .userKey(userKey)
                    .created(created)
                    .modified(modified)
                    .now(now)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param apiKey
     * @param userId : 이메일 형식
     * @return
     */
    public SearchAccountMemberResponse searchAccountMember(String apiKey, String userId) {
        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/member/search", "POST",
                "{\"apiKey\": \"" + apiKey + "\", \"userId\": \"" + userId + "\"}");

        if (httpResponse == null) return null;

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return SearchAccountMemberResponse.builder()
                    .result("error")
                    .build();

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String result = jsonNode.get("code").asText();
            String resultUserId = jsonNode.get("payload").get("userId").asText();
            String userName = jsonNode.get("payload").get("userName").asText();
            String institutionCode = jsonNode.get("payload").get("institutionCode").asText();
            String userKey = jsonNode.get("payload").get("userKey").asText();
            String created = jsonNode.get("payload").get("created").asText();
            String modified = jsonNode.get("payload").get("modified").asText();
            String now = jsonNode.get("now").asText();

            return SearchAccountMemberResponse.builder()
                    .result(result)
                    .userId(resultUserId)
                    .userName(userName)
                    .institutionCode(institutionCode)
                    .userKey(userKey)
                    .created(created)
                    .modified(modified)
                    .now(now)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
