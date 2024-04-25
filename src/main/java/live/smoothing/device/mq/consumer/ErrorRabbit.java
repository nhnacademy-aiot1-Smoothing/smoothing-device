package live.smoothing.device.mq.consumer;

import live.smoothing.device.mq.dto.BrokerErrorRequest;
import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorRabbit {
    private final BrokerService brokerService;
    private final SensorService sensorService;

    @RabbitListener(queues = "add-brokerError-queue", containerFactory = "jsonContainerFactory")
    public void receiveAddBrokerErrorMessage(BrokerErrorRequest request) {
        log.info("Received Add Broker message: {}", request);
        brokerService.addBrokerError(request);
    }

    @RabbitListener(queues = "add-sendError-queue", containerFactory = "jsonContainerFactory")
    public void receiveAddSendErrorMessage(SensorErrorRequest request) {
        log.info("Received Add Broker message: {}", request);
        sensorService.addSensorError(request);
    }
}
