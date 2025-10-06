package am.itspace.order.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RefreshScope
public class MessageController {

  @RateLimiter(name = "rateBreaker", fallbackMethod = "getMessageFallback")
  @GetMapping("/message")
  public String getMessage() {
    return "Hello Order";
  }

  public String getMessageFallback(Exception e) {
    log.error("error: {}", e.getMessage());
    return "Hello fallback";
  }

}
