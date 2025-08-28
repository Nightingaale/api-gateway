# API-gateway (Developer)
A Spring Cloud Gateway microservice that acts as a single entry point for client requests, efficiently routing them to the microservices
<p align="left">
  <img src="https://skillicons.dev/icons?i=java,spring,docker,gradle,git"/>
</p>

# Features

- Authentication & Authorization: Validates JWT tokens or OAuth2 credentials to ensure secure access to APIs
- Rate Limiting & Throttling: Protects microservices from excessive traffic by enforcing request limits per client or API
- Load Balancing: Distributes incoming requests across multiple backend instances to improve performance and reliability

# Prerequisites
- Java Development Kit(JDK): Version 17 or higher
- Gradle: For project build and dependency management
- Docker: For building containers
- Vault: Keep your API keys, JWT tokens & secure data
  
  ```sh
  java --version
  gradle --version
  docker --version
  ```
