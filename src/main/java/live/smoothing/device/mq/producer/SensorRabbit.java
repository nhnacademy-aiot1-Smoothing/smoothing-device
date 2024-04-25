package live.smoothing.device.mq.producer;

import live.smoothing.device.mq.dto.TopicRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorRabbit implements SensorMQ {

    private final RabbitTemplate rabbitTemplate;

    private final String topic_add = "add-topic-queue";
    private final String topic_delete = "delete-topic-queue";

    @Override
    public void saveTopic(Integer brokerId, String topic) {
        rabbitTemplate.convertAndSend(topic_add, new TopicRequest(brokerId, topic));
    }

    @Override
    public void deleteTopic(Integer brokerId, String topic) {
        rabbitTemplate.convertAndSend(topic_delete, new TopicRequest(brokerId, topic));
    }
}
