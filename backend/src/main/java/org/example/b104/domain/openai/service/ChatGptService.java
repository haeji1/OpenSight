package org.example.b104.domain.openai.service;

//import io.github.flashvayne.chatgpt.service.ChatgptService;
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

@Service
public class ChatGptService {


    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;
    private final RestTemplate restTemplate;

    public ChatGptService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String chat(String prompt){
        GPTRequest request = new GPTRequest(
                model,prompt,1,256,1,2,2);

        GPTResponse gptResponse = restTemplate.postForObject(
                apiUrl
                , request
                , GPTResponse.class
        );
        return gptResponse.getChoices().get(0).getMessage().getContent();
    }
}
