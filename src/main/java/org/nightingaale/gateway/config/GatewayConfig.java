package org.nightingaale.gateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                // Swagger(example)
                .route("auth_service_docs", r -> r.path("/aggregate/auth-service/v1/openapi-docs")
                        .filters(f -> f.rewritePath("/aggregate/auth-service/v1/openapi-docs", "/v1/openapi-docs"))
                        .uri("lb://auth-service"))

                // Main path(example)
                .route("auth_service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("authServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://auth-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackHandler() {
        return RouterFunctions.route()
                .GET("/fallback", request -> ServerResponse
                        .status(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE)
                        .bodyValue("[Service now unavailable. Try again later.]"))
                .build();
    }
}
