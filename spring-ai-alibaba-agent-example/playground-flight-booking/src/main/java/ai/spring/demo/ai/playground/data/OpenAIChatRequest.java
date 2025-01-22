package ai.spring.demo.ai.playground.data;

import lombok.Data;

import java.util.List;

/**
 * @author jackjack
 * @date 11/1/25
 **/
@Data
public class OpenAIChatRequest {
    private String model;
    private boolean stream;
    private List<OpenAIChatMessage> messages;
}
