package am.itspace.notification.service.service;

import am.itspace.notification.service.dto.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final ObjectMapper objectMapper;

  public void processOrderEvent(ConsumerRecord<Long, String> record) {
    try {
      OrderResponse event = this.objectMapper.readValue(record.value(), OrderResponse.class);
      log.info("Order events received successfully: {}", event);
    } catch (JsonProcessingException e) {
      log.error("Error processing order event: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
