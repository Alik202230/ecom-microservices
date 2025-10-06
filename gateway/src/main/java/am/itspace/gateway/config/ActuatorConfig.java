package am.itspace.gateway.config;

import org.springframework.boot.actuate.health.SimpleStatusAggregator;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

  @Bean
  public StatusAggregator statusAggregator() {
    return new SimpleStatusAggregator();
  }

}
