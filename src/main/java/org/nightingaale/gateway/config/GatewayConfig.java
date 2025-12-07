package org.nightingaale.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GatewayConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("auth_service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("authServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://auth-service"))

                .route("user_service", r -> r.path("/api/v1/users/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("userServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://user-service"))

                .route("order_service", r -> r.path("/api/v1/order/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("orderServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://order-service"))

                .route("payment_service", r -> r.path("/api/v1/payment/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("paymentServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://payment-service"))

                .route("catalog_service", r -> r.path("/api/v1/catalog/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("catalogServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://catalog-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackHandler() {
        return route()
                .GET("/fallback", request -> ServerResponse
                        .status(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE)
                        .bodyValue("[Service now unavailable. Try again later.]"))
                .build();
    }
}
