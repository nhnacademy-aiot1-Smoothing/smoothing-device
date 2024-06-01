package live.smoothing.device.mq.consumer;

import live.smoothing.device.mq.dto.BrokerErrorRequest;
import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 에러 메시지 소비자<br>
 * Rule Engine 에서 오는 에러 메시지를 소비하는 클래스
 *
 * @author 우혜승
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitErrorConsumer {
    private final BrokerService brokerService;
    private final SensorService sensorService;

    /**
     * 브로커 에러 메시지 수신<br>
     * 브로커 에러 메시지를 수신하여 저장한다.
     *
     * @param request 브로커 에러 메시지
     */
    @RabbitListener(queues = "add-brokerError-queue", containerFactory = "jsonContainerFactory")
    public void receiveAddBrokerErrorMessage(BrokerErrorRequest request) {
//        brokerService.addBrokerError(request);
    }

    /**
     * 센서 에러 메시지 수신<br>
     * 센서 에러 메시지를 수신하여 저장한다.
     *
     * @param request 센서 에러 메시지
     */
    @RabbitListener(queues = "add-sensorError-queue", containerFactory = "jsonContainerFactory")
    public void receiveAddSendErrorMessage(SensorErrorRequest request) {
        sensorService.addSensorError(request);
    }
}
