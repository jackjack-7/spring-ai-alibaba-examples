package ai.spring.demo.ai.playground.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author jackjack
 * @Date 11/1/25
 **/
@Data
@AllArgsConstructor
public class OpenAIChatMessage {
    public static final String ROLE_AI = "ai";
    public static final String ROLE_USER = "user";

    private String cid;
    private String role;
    private String content;
}
