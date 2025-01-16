package ai.spring.demo.ai.playground.data;

import lombok.Data;

import java.util.List;

/**
 * @author jackjack
 * @date 11/1/25
 **/
@Data
public class ChatResponse {
    private Long cid;
    private String role;
    private String content;
    private List<String> suggestions;
}
