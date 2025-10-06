package am.itspace.order.producer;

import am.itspace.order.dto.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventsProducer {

  @Value("${spring.kafka.topic}")
  private String topic;
  private final KafkaTemplate<Long, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public void sendOrderEvent(OrderResponse event) {
    try {
      Long key = event.getId();
      String value = this.objectMapper.writeValueAsString(event);
      SendResult<Long, String> sendResult = this.kafkaTemplate.send(topic, key, value).get(3, TimeUnit.SECONDS);
      soSuccess(key, value, sendResult);
    } catch (JsonProcessingException ex) {
      log.error("Cannot convert order event to JSON : {}", ex.getMessage());
      throw new RuntimeException(ex);
    } catch (ExecutionException | InterruptedException | TimeoutException ex) {
      onFailure(ex);
      log.error("Cannot send order event : {}", ex.getMessage());
      throw new RuntimeException("Order event execution error", ex);
    }
  }

  private void onFailure(Throwable ex) {
    log.error("Cannot send order event to JSON : {}", ex.getMessage());
  }

  private void soSuccess(Long key, String value, SendResult<Long, String> sendResult) {
    log.info(
        "Order event successfully published. key={},\n value={},\n partition={}\n",
        key, value, sendResult.getProducerRecord().partition()
    );
  }
}
