package com.zr.chat.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shuWq
 * @Description chat入口类
 * @Date 2024/5/20 15:08
 * @Version 1.0
 */


public class ChatController {
    public static void main(String[] args) {
        HttpResponse execute = null;
        try {
            String apiKey = "sk-proj-wevNykCTe8s9ABpTy8tLT3BlbkFJUI9oCRqxaCRE287HBJBp"; // 替换为您的ChatGPT API密钥
            String endpoint = "https://api.openai.com/v1/chat/completions"; // ChatGPT API端点

            // 创建HttpHeaders对象
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json"); // 设置Content-Type为application/json
            headers.put("Authorization", apiKey); // 设置授权 token

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", new HashMap<String, String>() {
                {
                    put("role", "user");
                    put("content", "Hello, how are you?");
                }
            });
            execute = HttpUtil.createPost(endpoint)
                    .headerMap(headers, false)
                    .bearerAuth(apiKey)
                    .body(JSON.toJSONString(requestBody))
                    .timeout(3 * 1000)
                    .setHttpProxy("127.0.0.1", 7890)
                    .execute();
            String body = execute.body();
            if (execute.isOk()) {
                JSONObject jsonObject = JSON.parseObject(body);
                System.out.println(jsonObject.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (execute != null) {
                execute.close();
            }
        }
    }
}