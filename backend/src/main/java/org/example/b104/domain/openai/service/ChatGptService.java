package org.example.b104.domain.openai.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.b104.domain.openai.config.GPTConfig;
import org.example.b104.domain.openai.request.GPTRequest;
import org.example.b104.domain.openai.response.GPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.example.b104.domain.openai.config.ChatGPTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ChatGptService {

    private final ChatGPTConfig chatGPTConfig;
    private final ChatgptService chatgptService;

    @Autowired
    public ChatGptService(ChatGPTConfig chatGPTConfig, ChatgptService chatgptService) {
        this.chatGPTConfig = chatGPTConfig;
        this.chatgptService = chatgptService;
    }

    @Value("${gpt.api.url}")
    private String gptApiUrl;

    @Value("${gpt.api.key}")
    private String gptApiKey;

    public String generateText(String prompt) {
        String responseBody = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(gptApiUrl);

            // GPT API 요청 헤더 설정
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + gptApiKey);

            // GPT API 요청 바디 설정
            String requestBody = "{\"prompt\": \"" + prompt + "\"}";
            httpPost.setEntity(new StringEntity(requestBody));

            // GPT API 호출 및 응답 처리
            HttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }
    private final ChatGPTConfig chatGPTConfig;
    private final ChatgptService chatgptService;

    @Autowired
    public ChatGptService(ChatGPTConfig chatGPTConfig, ChatgptService chatgptService) {
        this.chatGPTConfig = chatGPTConfig;
        this.chatgptService = chatgptService;
    }

    @Value("${gpt.api.url}")
    private String gptApiUrl;

    @Value("${gpt.api.key}")
    private String gptApiKey;

    public String generateText(String prompt) {
        String responseBody = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(gptApiUrl);

            // GPT API 요청 헤더 설정
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + gptApiKey);

            // GPT API 요청 바디 설정
            String requestBody = "{\"prompt\": \"" + prompt + "\"}";
            httpPost.setEntity(new StringEntity(requestBody));

            // GPT API 호출 및 응답 처리
            HttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

}
