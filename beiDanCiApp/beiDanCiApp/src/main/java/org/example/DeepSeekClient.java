package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class DeepSeekClient {
    private static final String API_URL = "http://localhost:1234/v1/chat/completions";
    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void test() throws IOException {
        System.out.println(sendMessageToAI("jj"));
    }

    public String sendMessageToAI(String userInput) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("🔄 输入 'exit' 退出");
            String requestBody = String.format(
                    "{\"model\": \"DeepSeek-R1\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"temperature\": 0.7}",
                    userInput.replace("\"", "\\\"")
            );
            String aiResponse = sendPostRequest(API_URL, requestBody);
            return aiResponse;
    }

    private static String sendPostRequest(String urlString, String requestBody) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestBody.getBytes("utf-8"));
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8")
        )) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        // 使用 Jackson 解析 JSON
        JsonNode rootNode = mapper.readTree(response.toString());
        if (rootNode.has("error")) {
            return "API 错误: " + rootNode.get("error").asText();
        }

        JsonNode choicesNode = rootNode.path("choices");
        if (choicesNode.isEmpty()) {
            return "无效响应: choices 为空";
        }

        JsonNode messageNode = choicesNode.get(0).path("message");
        if (messageNode.isMissingNode()) {
            return "无效响应: 无 message 字段";
        }

        return messageNode.path("content").asText();
    }
}