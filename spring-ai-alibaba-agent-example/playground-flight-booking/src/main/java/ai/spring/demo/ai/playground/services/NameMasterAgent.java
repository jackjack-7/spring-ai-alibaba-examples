/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.spring.demo.ai.playground.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ai.spring.demo.ai.playground.data.OpenAIChatMessage;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * * @author jackjack
 */
@Service
public class NameMasterAgent {

	private ChatClient chatClient;

//	private final ChatClient recommendClient;

	public NameMasterAgent(OpenAiChatModel chatModel, VectorStore vectorStore, ChatMemory chatMemory) {
		updateChatClient(chatModel, vectorStore, chatMemory);

//		this.recommendClient = modelBuilder
//				.defaultSystem("""
//						你是一个给新生儿起名字的国学大师，你正在面向你的客户，帮你的客户给他们的宝宝起名字。
//						你需要根据历史的聊天记录，以你的专业，给客户推荐一些起名字的思路。
//						你能够从国学典籍、名字音律、相关城市、历史名人、当代明星等方面获取灵感，给客户的宝宝起符合客户需求，并且寓意好、好听的名字。
//						你也能够
//						如果需要，您可以调用相应函数辅助完成。
//						每次给2个建议，建议用英文的逗号分隔。建议输出的格式为：
//						...
//						建议1,建议2
//						...
//						请讲中文。
//						今天的日期是 {current_date}.
//					""")
//				.defaultAdvisors(
//						new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
//						// new VectorStoreChatMemoryAdvisor(vectorStore)),
//
//						new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
//						// new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
//						// 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
//
//						new LoggingAdvisor())
//
//				.defaultFunctions("getBookingDetails", "changeBooking", "cancelBooking") // FUNCTION CALLING
//
//				.build();
		// @formatter:on
	}

	private void updateChatClient(OpenAiChatModel chatModel, VectorStore vectorStore, ChatMemory chatMemory) {
		ChatClient.Builder builder = ChatClient.builder(chatModel);

		// @formatter:off
		this.chatClient = builder
				.defaultOptions(OpenAiChatOptions.builder()
					.withTemperature(0.95d)
					.withTopP(0.95d)
					.build())
				.defaultSystem("""
						# 基本信息
						你是一个给新生儿起名字的国学大师，你正在面向你的客户，帮你的客户给他们的宝宝起名字。
						你正在通过在线聊天系统与客户互动，以沟通的方式了解客户的起名需求，并以你的专业为客户提供起名字的建议。
						每一次给客户新的起名字的建议的时候，不要跟之前起过的名字重复。
						你会先了解客户宝宝的生日、姓、性别这三个基本信息，如果客户没有提供需要与客户确认。
						你能够从国学典籍、名字音律、相关城市、历史名人、当代明星等方面获取灵感，给客户的宝宝起符合客户需求，并且寓意好、好听的名字。
						你也能够回答起名字需要考虑的因素的相关知识，相关知识可以从知识库中查找，并根据知识库内容给用户回复。
						如果需要，您可以调用相应函数辅助完成。。
						你通常会从国学典籍开始，并根据不断与客户沟通的过程，逐渐帮客户起到他们满意的名字。

						# 回复内容格式
						回复内容中姓名要以粗体显示，颜色值为
						
						# 推荐问法
						在回复的最后，生成2个给用户建议推荐问法。可以根据如下列举的推荐思路来推荐
						
						### 推荐问法的思路包括
						1. 概念学习：提示用户问起名字要考虑的点，例如：
							a. 取名字需要考虑什么要素
							b. 取名字需要避讳什么
							c. 取名字时，音律要注意什么
								d. 取名字时，汉字五行有什么学问
						2. 同义词：跟起过的名字相关的名字
						3. 存量佳名：推荐最受欢迎的名字
						4. 风格：推荐现代风、古风、诗经风、民国风、唐风、宋风、霸气风、甜美风、淑女风等风格的名字
						5. 五行：从五行相生角度，给我几个好名字。
						6. 特定事物和喜好：例如城市、幸运颜色、节气、信物
						7. 包含某个字的名字：例如可、之、以、子、于、若 、如、其、乃、亦、宜、伊、竟、恒、常、新、书、悦、梦、安、舒、灿、知、乐、嘉、睿、麦、亮等
						8. 品格：长大后希望他有美好品格，例如善良、宽厚、赤诚、率真、优雅、温婉、俊逸、灵动、潇洒、正直、诚实、谦逊、豁达、果敢、勇敢、乐观、勤奋等
						9. 四季：根据出生的季节推荐
						10. 星座：根据出生的星座推荐
						
						### 推荐问法的输出的格式为JSON的array格式，推荐问法之间用英文逗号(，)分隔：
						[推荐问法1,推荐问法2]
						
						### 推荐问法举例
						以下是推荐问法举例：
						[我的孩子姓赵，生日是{current_date},起名字需要考虑什么要素]
						[我想要好听的名字,我想要寓意好的名字]
						[我想要跟某个明星有关的名字,我想要跟某个城市有关的名字]
						
						请讲中文。
						今天的日期是 {current_date}.
					""")
				.defaultAdvisors(
						new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
						// new VectorStoreChatMemoryAdvisor(vectorStore)),

//						new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
						// new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
						// 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
						
						new LoggingAdvisor())
						
				.defaultFunctions("getBookingDetails", "changeBooking", "cancelBooking") // FUNCTION CALLING

				.build();
	}

	public Flux<OpenAIChatMessage> chat(String chatId, String userMessageContent) {
		return this.chatClient.prompt()
			.system(s -> s.param("current_date", LocalDate.now().toString()))
			.user(userMessageContent)
			.advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
			.stream()
			.content()
			.map(content -> new OpenAIChatMessage(chatId, OpenAIChatMessage.ROLE_AI, content));
	}

//	public List<String> recommend(String chatId, String userMessageContent) {
//		String recommendText = this.recommendClient.prompt()
//				.system(s -> s.param("current_date", LocalDate.now().toString()))
//				.user(userMessageContent)
//				.advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
//				.call()
//				.content();
//		return Arrays.asList(StringUtils.delimitedListToStringArray(recommendText,","));
//	}

}