package org.main.recommendation.service;

import org.main.recommendation.entity.Embedding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


@Service
public class GeminiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmbeddingService embeddingService;

    @Value("${GEMINI_URL}")
    private String geminiApiUrl;

    @Value("${GEMINI_KEY}")
    private String geminiApiKey;

    public float[] getEmbeddingForString(String input) {
        String url = String.format("%s/models/text-embedding-004:embedContent?key=%s", geminiApiUrl, geminiApiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestJson = String.format("{\"content\": {\"parts\": [{\"text\": \"%s\"}]}}", input);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("embedding")) {
            Map<String, Object> embeddingMap = (Map<String, Object>) responseBody.get("embedding");
            if (embeddingMap.containsKey("values")) {
                List<Double> values = (List<Double>) embeddingMap.get("values");
                float[] floatValues = new float[values.size()];
                for (int i = 0; i < values.size(); i++) {
                    floatValues[i] = values.get(i).floatValue();
                }
                return floatValues;
            }
        }

        return new float[0];
    }
}
