package am.itspace.notification.service.consumer;

import am.itspace.notification.service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

  private final NotificationService notificationService;
  
  @KafkaListener(topics = "order-events", groupId = "order-events-group")
  public void onMessage(ConsumerRecord<Long, String> record) {
    log.info("Received event: {}", record.value());
    this.notificationService.processOrderEvent(record);
  }

}
