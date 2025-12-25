package ecomhub.promotiondiscount.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageQueueService {
    // Uncomment nếu dùng Kafka thực tế
    // private final KafkaTemplate<String, PromotionUpdateMessage> kafkaTemplate;

    public void sendPromotionUpdateMessage(UUID id, String action) {
        PromotionUpdateMessage msg = new PromotionUpdateMessage(id, action);
        // kafkaTemplate.send("promotion-topic", msg);
        log.info("Gửi message: {} - {}", id, action);
    }

    @Getter
    @Setter
    public static class PromotionUpdateMessage {
        private UUID promotionId;
        private String action;

        public PromotionUpdateMessage(UUID promotionId, String action) {
            this.promotionId = promotionId;
            this.action = action;
        }
    }
}