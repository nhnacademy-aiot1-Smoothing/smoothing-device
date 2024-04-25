package live.smoothing.device.mq.producer;

import live.smoothing.device.mq.dto.BrokerDeleteRequest;
import live.smoothing.device.mq.dto.BrokerGenerateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BrokerRabbit implements BrokerMQ {
    private final RabbitTemplate rabbitTemplate;

    private final String broker_add = "add-broker-queue";
    private final String broker_delete = "delete-broker-queue";


    @Override
    public void saveBroker(String brokerIp, Integer brokerPort, Integer brokerId, String protocolType) {
        rabbitTemplate.convertAndSend(broker_add, new BrokerGenerateRequest(brokerIp, brokerPort, brokerId, protocolType));
    }

    @Override
    public void deleteBroker(Integer brokerId) {
        rabbitTemplate.convertAndSend(broker_delete, new BrokerDeleteRequest(brokerId));
    }
}
