package ecomhub.cart.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    public static final String RESOURCE_TOPIC = "resource-events";
    public static final String USER_TOPIC = "user-events";

    @Bean
    public NewTopic resourceTopic() {
        return TopicBuilder.name(RESOURCE_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(USER_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}