package ai.spring.demo.ai.playground.client;

import ai.spring.demo.ai.playground.data.ChatRequest;
import ai.spring.demo.ai.playground.data.OpenAIChatRequest;
import ai.spring.demo.ai.playground.services.CustomerSupportAssistant;
import ai.spring.demo.ai.playground.services.NameMasterAgent;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RequestMapping("/api/namemaster")
@RestController
public class NameMasterController {

	private final NameMasterAgent agent;

	public NameMasterController(NameMasterAgent agent) {
		this.agent = agent;
	}

	@CrossOrigin("*")
	@PostMapping(path="/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> chat(@RequestBody ChatRequest request) {
		return agent.chat(String.valueOf(request.getCid()), request.getContent());
	}

}
