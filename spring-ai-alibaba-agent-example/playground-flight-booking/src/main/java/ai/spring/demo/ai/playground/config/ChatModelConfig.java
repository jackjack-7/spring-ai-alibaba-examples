package ai.spring.demo.ai.playground.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatModelConfig {

    @Bean
    public Map<String, ChatModel> chatModelMap(OpenAiChatModel openAiChatModel) {
        Map<String, ChatModel> chatModelMap = new HashMap<>();
        chatModelMap.put("openai", openAiChatModel);
        chatModelMap.put("qwen", openAiChatModel);
        return chatModelMap;
    }

}
