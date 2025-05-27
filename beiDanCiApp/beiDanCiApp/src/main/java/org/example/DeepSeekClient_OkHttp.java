package org.example;
import okhttp3.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeepSeekClient_OkHttp {
    private static final String API_URL = "http://localhost:1234/v1/chat/completions";
    private static final OkHttpClient client = new OkHttpClient();

//    @Test
//    public void test() throws IOException {
//        String 你好 = sendMessageToAI("jj");
//        System.out.println(你好);
//    }
    public static void main(String[] args) throws IOException {
//    public String sendMessageToAI(String userInput) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("\n👤 你: ");
            String userInput = reader.readLine().trim();

            if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                System.out.println("👋 对话结束");
                break;
            }

            String json = String.format(
                    "{\"model\": \"DeepSeek-R1\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"temperature\": 0.7}",
                    userInput.replace("\"", "\\\"")
            );

            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
            Request request = new Request.Builder().url(API_URL).post(body).build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                String aiReply = responseBody.split("\"content\":\"")[1].split("\"")[0];
                System.out.println("\n🤖 AI: " + aiReply.replace("\\n", "\n"));
//                System.out.println(aiReply.replace("\\n", "\n"));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}