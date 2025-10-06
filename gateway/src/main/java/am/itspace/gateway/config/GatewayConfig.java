package am.itspace.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class GatewayConfig {

  private static final String LB_PRODUCT_SERVICE = "lb://PRODUCT-SERVICE";
  private static final String LB_USER_SERVICE = "lb://USER-SERVICE";
  private static final String LB_ORDER_SERVICE = "lb://ORDER-SERVICE";
  private static final String EUREKA_SERVER_URL = "http://localhost:8761";
  private static final String PRODUCT_SERVICE = "product-service";
  private static final String USER_SERVICE = "user-service";
  private static final String ORDER_SERVICE = "order-service";
  private static final String EUREKA_SERVER = "eureka-server";
  private static final String EUREKA_SERVER_STATIC = "eureka-server-static";

  @Bean
  public RedisRateLimiter redisRateLimiter() {
    return new RedisRateLimiter(10, 20, 1);
  }

  @Bean
  public KeyResolver hostNamekeyResolver() {
    return exchange -> Mono.just(
        Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName()
    );
  }

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("product-service", router -> router.path("/api/products/**")
            .filters(filter -> filter.circuitBreaker(config -> config
                    .setName("ecommerceBreaker")
                    .setFallbackUri("forward:/fallback/products"))
                .requestRateLimiter(config ->
                    config.setRateLimiter(redisRateLimiter())
                        .setKeyResolver(hostNamekeyResolver())))
//            .filters(filter -> filter.rewritePath("/products(?<segment>/?.*)", "/api/products${segment}"))
            .uri(LB_PRODUCT_SERVICE))
        .route("user-service", router -> router.path("/api/users/**")
//            .filters(filter -> filter.rewritePath("/users(?<segment>/?.*)", "/api/users${segment}"))
            .uri(LB_USER_SERVICE))
        .route("order-service", router -> router.path("/api/orders/**", "/api/cart-items/**")
//            .filters(filter -> filter.rewritePath("/(?<segment>.*)", "/api/${segment}"))
            .uri(LB_ORDER_SERVICE))
        .route("eureka-server", router -> router.path("/eureka/main")
            .filters(filter -> filter.rewritePath("/eureka/main", "/"))
            .uri(EUREKA_SERVER_URL))
        .route("eureka-server-static", router -> router.path("/eureka/**").uri(EUREKA_SERVER_URL))
        .build();
  }

}
