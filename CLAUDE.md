# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is Spring AI Alibaba Examples - a comprehensive collection of 21+ example modules demonstrating AI capabilities using Spring AI and Spring AI Alibaba frameworks. It's a Java 17 Maven multi-module project showcasing everything from basic "Hello World" to advanced RAG, multi-agent systems, and full-stack AI playground applications.

## Development Commands

### Build & Test
```bash
make build          # Build project (skips tests)
make test           # Run tests
mvn clean install -DskipTests  # Alternative Maven build
```

### Code Quality
```bash
make format-check    # Check Java formatting
make format-fix      # Fix Java formatting using spring-javaformat
make checkstyle-check # Run checkstyle validation
make lint           # Multi-language linting (YAML, codespell, newlines)
```

### License Management
```bash
make licenses-check  # Check license headers
make licenses-fix    # Add missing license headers
```

## Architecture & Structure

### Multi-Module Organization
The project is organized into specialized modules by functionality:

- **Core Examples**: `spring-ai-alibaba-helloworld`, `spring-ai-alibaba-chat-example`
- **Advanced AI**: `spring-ai-alibaba-rag-example`, `spring-ai-alibaba-agent-example`  
- **Full-Stack**: `spring-ai-alibaba-playground` (React + Spring Boot)
- **Specialized**: MCP, graph workflows, observability, multi-model integrations
- **Media Processing**: Audio, video, image processing examples
- **Use Cases**: Classification, translation, summarization

### Technology Stack
- **Backend**: Spring Boot 3.4.0, Spring AI 1.0.0, Spring AI Alibaba 1.0.0.3-SNAPSHOT
- **Frontend** (playground): React 18.3.1 + TypeScript + Vite + Ant Design
- **AI Platforms**: Alibaba DashScope (primary), OpenAI, Azure OpenAI, Ollama, Moonshot, DeepSeek, ZhipuAI
- **Vector Stores**: Milvus, PGVector, ElasticSearch, Redis, AnalyticDB, OceanBase

### Configuration Management
- Environment variables for API keys (AI_DASHSCOPE_API_KEY, etc.)
- YAML-based configuration files in each module
- Profile-based configuration (dev, prod)
- Docker Compose services for local development (ElasticSearch, Milvus, MySQL, etc.)

## Code Standards

### Required Practices
- Complete examples with README.md and method parameter documentation
- Apache License 2.0 headers on all files (enforced by make targets)
- Spring Java Format code style (enforced)
- Jackson for JSON serialization
- Apache Commons Utils or Spring utilities preferred
- Modular approach with meaningful module names

### Testing
- Integration tests focusing on AI service interactions
- Standard Spring Boot Test framework
- Most modules have tests in `src/test/java`
- Run with `mvn test` or `make test`

## Development Environment

### Local Services
Available via Docker Compose in `/docker-compose/`:
- Vector databases: ElasticSearch, Milvus, PGVector, Redis
- Databases: MySQL, OceanBase  
- Observability: Zipkin
- LLM: Ollama for local model deployment
- Gateway: Higress

### Key Configuration Files
- `/pom.xml` - Root Maven configuration and module definitions
- `/Makefile` - Build system entry point  
- `/tools/make/*.mk` - Specialized build targets
- `/tools/src/checkstyle/checkstyle.xml` - Code style rules
- Individual module `README.md` files for specific functionality

## Working with Examples

Each module is designed to be self-contained with:
- Comprehensive README.md explaining setup and usage
- Configuration properties for AI service connections
- Sample code demonstrating specific AI capabilities
- Integration tests validating functionality

The playground module (`spring-ai-alibaba-playground`) serves as the most comprehensive full-stack example with both frontend and backend components.