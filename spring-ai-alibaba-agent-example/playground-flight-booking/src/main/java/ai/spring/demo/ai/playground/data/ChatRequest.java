package ai.spring.demo.ai.playground.data;

import lombok.Data;

import java.util.List;

/**
 * @author jackjack
 * @date 11/1/25
 **/
@Data
public class ChatRequest {
    private Long cid;
    private String content;
}
