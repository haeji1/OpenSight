package org.example.b104.domain.openai.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.b104.domain.openai.config.ChatGPTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {

    private final ChatGPTConfig chatGPTConfig;
    private final ChatgptService chatgptService;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;
    private final RestTemplate restTemplate;

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
