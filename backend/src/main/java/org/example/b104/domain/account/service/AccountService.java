package org.example.b104.domain.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.example.b104.domain.account.controller.request.AccountRequestHeader;
import org.example.b104.domain.account.controller.request.InquireBankAccountTypesRequest;
import org.example.b104.domain.account.controller.response.*;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.BankApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

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

    public InquireBankAccountTypesResponse inquireBankAccountTypes(String apiKey) {
        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireBankAccountTypes")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireBankAccountTypes")
                .apiKey(apiKey)
                .build();

        accountRequestHeader.init();

        InquireBankAccountTypesRequest request = InquireBankAccountTypesRequest.builder()
                .Header(accountRequestHeader)
                .build();

        //아무리봐도 이렇게 하면 안되는 거 같은데 일단 작동함
        //추후 수정 필요
        //*********************  이 부 분 **************************
        JsonNode jsonNode = objectMapper.valueToTree(request);
        String jsonNodeString = jsonNode.toString();
        char [] jsonNodeStringArray = jsonNodeString.toCharArray();
        jsonNodeStringArray[2] = 'H'; // 지맘대로 H를 h로 바꿔서 일단 하드코딩
        String jsonNodeString2 = new String(jsonNodeStringArray);
        //*********************************************************

        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/edu/account/inquireBankAccountTypes", "POST",
                jsonNodeString2);

//        System.out.println("[+] Status Code : " + httpResponse.statusCode());
//        System.out.println("[+] Body : " +httpResponse.body());
//        System.out.println("##################################################");

        JSONObject jsonObject = new JSONObject(httpResponse.body());
        JSONObject resultHeader = jsonObject.getJSONObject("Header");
        JSONArray RECArray = jsonObject.getJSONArray("REC");
        try{
            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            REC recList[] = objectMapper.readValue(RECArray.toString(), REC[].class);
            return InquireBankAccountTypesResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(recList)
                    .build();
        } catch(Exception e){
            e.printStackTrace();
        }
        return InquireBankAccountTypesResponse.builder()
                .result("error")
                .build();
    }

    public OpenAccountResponse openAccount(String apiKey, String accountTypeUniqueNo, String userKey) {
        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("openAccount")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("openAccount")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/edu/account/openAccount", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"accountTypeUniqueNo\": \"" + accountTypeUniqueNo + "\"}");

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return OpenAccountResponse.builder()
                    .result("error")
                    .build();

        try {

            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");
            String resultBankCode = REC.getString("bankCode");
            String resultAccountNo = REC.getString("accountNo");

            AccountResponseHeader accountResp = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);

            return OpenAccountResponse.builder()
                    .result("success")
                    .Header(accountResp)
                    .bankCode(resultBankCode)
                    .accountNo(resultAccountNo)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquireAccountListResponse inquireAccountList(String apiKey, String userKey) {
        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireAccountList")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireAccountList")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://finapi.p.ssafy.io/ssafy/api/v1/edu/account/inquireAccountList", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + "}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());


        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireAccountListResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONArray RECArray = jsonObject.getJSONArray("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            REC recList[] = objectMapper.readValue(RECArray.toString(), REC[].class);

            return InquireAccountListResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(recList)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
