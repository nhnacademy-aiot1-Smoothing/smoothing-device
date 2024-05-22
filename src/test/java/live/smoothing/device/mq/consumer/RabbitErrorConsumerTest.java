package live.smoothing.device.mq.consumer;

import live.smoothing.device.broker.service.BrokerService;
import live.smoothing.device.mq.dto.BrokerErrorRequest;
import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.service.SensorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitErrorConsumerTest {

    @Mock
    private BrokerService brokerService;

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private RabbitErrorConsumer rabbitErrorConsumer;

    @Test
    void receiveAddBrokerErrorMessage() {
        BrokerErrorRequest request = new BrokerErrorRequest();

        rabbitErrorConsumer.receiveAddBrokerErrorMessage(request);

        verify(brokerService).addBrokerError(request);
    }

    @Test
    void receiveAddSendErrorMessage() {
        SensorErrorRequest request = new SensorErrorRequest();

        rabbitErrorConsumer.receiveAddSendErrorMessage(request);

        verify(sensorService).addSensorError(request);
    }
}