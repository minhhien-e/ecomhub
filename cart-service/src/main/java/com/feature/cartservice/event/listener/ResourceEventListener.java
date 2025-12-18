package com.feature.cartservice.event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feature.cartservice.config.KafkaConfig;
import com.feature.cartservice.event.model.ResourceEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ResourceEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = KafkaConfig.RESOURCE_TOPIC, groupId = "cart-service-group")
    public void handleResourceEvent(String message) {
        try {
            ResourceEvent event = objectMapper.readValue(message, ResourceEvent.class);
            System.out.println("Nhận event từ service resource: " + event);


        } catch (Exception e) {
            System.err.println(" Lỗi khi xử lý event: " + e.getMessage());
        }
    }
}
