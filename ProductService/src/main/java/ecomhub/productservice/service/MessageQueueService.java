package ecomhub.productservice.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageQueueService {

    public void sendInventoryUpdateMessage(UUID variantId, int quantity) {
        // Stub: chỉ log ra, chưa gửi Kafka thật
        System.out.printf("[MessageQueueService] Inventory update -> variantId=%s, quantity=%d%n",
                variantId, quantity);
    }

    @Setter
    @Getter
    public static class InventoryUpdateMessage {
        private UUID variantId;
        private int quantity;

        public InventoryUpdateMessage(UUID variantId, int quantity) {
            this.variantId = variantId;
            this.quantity = quantity;
        }
    }
}
