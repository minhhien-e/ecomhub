package ecomhub.cart.event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.cart.config.KafkaConfig;
import ecomhub.cart.event.model.UserEvent;

import ecomhub.cart.service.CreateService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CreateService createService;


    public UserEventListener(CreateService createService1) {
        this.createService = createService1;
    }

    @KafkaListener(
            topics = KafkaConfig.USER_TOPIC,
            groupId = "cart-service-group"
    )
    public void handleUserEvent(String message) {
        try {
            UserEvent event = objectMapper.readValue(message, UserEvent.class);
            System.out.println("Nhận event từ service user: " + event);


            if ("USER_CREATED".equals(event.getEventType())) {

                if (event.getUserId() == null || event.getUserId().isEmpty()) {
                    System.err.println("UserEvent không chứa UserID.");
                    return;
                }

                String userIdString = event.getUserId();
                UUID userIdUuid = UUID.fromString(userIdString);
                System.out.println("Tiến hành tạo giỏ hàng cho UserID: " + userIdUuid);
                createService.createCart(userIdUuid);
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi xử lý UserEvent: " + e.getMessage());
        }
    }
}