package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.exception.BrokerNotFoundException;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorErrorLog;
import live.smoothing.device.sensor.entity.SensorType;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.exception.SensorErrorLogNotFoundException;
import live.smoothing.device.sensor.exception.SensorNotFoundException;
import live.smoothing.device.sensor.exception.SensorTypeNotFoundException;
import live.smoothing.device.sensor.repository.SensorErrorLogRepository;
import live.smoothing.device.sensor.repository.SensorRepository;
import live.smoothing.device.sensor.repository.SensorTypeRepository;
import live.smoothing.device.sensor.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class SensorServiceImplTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Mock
    private BrokerRepository brokerRepository;

    @Mock
    private SensorErrorLogRepository sensorErrorLogRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private RuleEngineAdapter ruleEngineAdapter;

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Test
    void saveSensor_sensorTypeNotFound_throwError() {
        SensorRegisterRequest sensorRegisterRequest = mock(SensorRegisterRequest.class);
        when(sensorRegisterRequest.getSensorType()).thenReturn("sensorType");
        when(sensorTypeRepository.findById(sensorRegisterRequest.getSensorType())).thenReturn(Optional.empty());

        assertThrows(SensorTypeNotFoundException.class, () -> sensorService.saveSensor(sensorRegisterRequest));
    }

    @Test
    void saveSensor_brokerNotFound_throwError() {
        SensorRegisterRequest sensorRegisterRequest = mock(SensorRegisterRequest.class);
        when(sensorRegisterRequest.getSensorType()).thenReturn("sensorType");
        when(sensorTypeRepository.findById(sensorRegisterRequest.getSensorType())).thenReturn(Optional.of(mock(SensorType.class)));
        when(sensorRegisterRequest.getBrokerId()).thenReturn(1);
        when(brokerRepository.findById(sensorRegisterRequest.getBrokerId())).thenReturn(Optional.empty());

        assertThrows(BrokerNotFoundException.class, () -> sensorService.saveSensor(sensorRegisterRequest));
    }

    @Test
    void saveSensor() {
        SensorRegisterRequest sensorRegisterRequest = mock(SensorRegisterRequest.class);
        when(sensorRegisterRequest.getSensorType()).thenReturn("sensorType");
        when(sensorRegisterRequest.getSensorName()).thenReturn("sensorName");
        when(sensorTypeRepository.findById(sensorRegisterRequest.getSensorType())).thenReturn(Optional.of(mock(SensorType.class)));
        when(sensorRegisterRequest.getBrokerId()).thenReturn(1);
        when(brokerRepository.findById(sensorRegisterRequest.getBrokerId())).thenReturn(Optional.of(mock(Broker.class)));
        when(sensorTypeRepository.getReferenceById(sensorRegisterRequest.getSensorType())).thenReturn(mock(SensorType.class));

        sensorService.saveSensor(sensorRegisterRequest);
        verify(sensorRepository, times(1)).save(any());
    }

    @Test
    void getSensors_brokerNotFound_throwError() {
        Integer brokerId = 1;
        when(brokerRepository.existsById(brokerId)).thenReturn(false);

        assertThrows(BrokerNotFoundException.class, () -> sensorService.getSensors(brokerId, null));
    }

    @Test
    void getSensors() {
        Integer brokerId = 1;
        when(brokerRepository.existsById(brokerId)).thenReturn(true);
        Page<SensorResponse> expected = mock(Page.class);
        when(sensorRepository.findByBrokerBrokerId(brokerId, null)).thenReturn(expected);
        when(expected.getContent()).thenReturn(List.of());

        SensorListResponse actual = sensorService.getSensors(brokerId, null);
        assertEquals(actual.getSensors(), expected.getContent());
        verify(sensorRepository, times(1)).findByBrokerBrokerId(brokerId, null);
    }

    @Test
    void updateSensor_sensorNotFound_throwError() {
        Integer sensorId = 1;
        when(sensorRepository.findById(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> sensorService.updateSensor(sensorId, mock(SensorUpdateRequest.class)));
    }

    @Test
    void updateSensor_sensorTypeNotFound_throwError() {
        Integer sensorId = 1;
        when(sensorRepository.findById(sensorId)).thenReturn(Optional.of(mock(Sensor.class)));
        SensorUpdateRequest sensorUpdateRequest = mock(SensorUpdateRequest.class);
        when(sensorUpdateRequest.getSensorType()).thenReturn("sensorType");
        when(sensorTypeRepository.findById(sensorUpdateRequest.getSensorType())).thenReturn(Optional.empty());

        assertThrows(SensorTypeNotFoundException.class, () -> sensorService.updateSensor(sensorId, sensorUpdateRequest));
    }

    @Test
    void updateSensor() {
        Integer sensorId = 1;
        SensorUpdateRequest sensorUpdateRequest = mock(SensorUpdateRequest.class);
        Sensor sensor = mock(Sensor.class);
        SensorType sensorType = mock(SensorType.class);
        when(sensorRepository.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(sensorUpdateRequest.getSensorType()).thenReturn("sensorType");
        when(sensorUpdateRequest.getSensorName()).thenReturn("sensorName");
        when(sensorTypeRepository.findById(sensorUpdateRequest.getSensorType())).thenReturn(Optional.of(sensorType));

        sensorService.updateSensor(sensorId, sensorUpdateRequest);
        verify(sensorRepository, times(1)).save(sensor);
        verify(sensor, times(1)).updateSensorName("sensorName");
        verify(sensor, times(1)).updateSensorType(sensorType);
    }

    @Test
    void deleteSensor_sensorNotFound_throwError() {
        Integer sensorId = 1;
        when(sensorRepository.findSensorWithTopicBySensorId(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> sensorService.deleteSensor(sensorId));
    }

    @Test
    void deleteSensor() {
        Integer sensorId = 1;
        Sensor sensor = mock(Sensor.class);
        Broker broker = mock(Broker.class);
        Topic t1 = mock(Topic.class);
        Topic t2 = mock(Topic.class);
        Set<Topic> topics = Set.of(t1, t2);
        when(sensorRepository.findSensorWithTopicBySensorId(sensorId)).thenReturn(Optional.of(sensor));
        when(sensor.getTopics()).thenReturn(topics);
        when(sensor.getBroker()).thenReturn(broker);
        when(broker.getBrokerId()).thenReturn(1);
        when(t1.getTopic()).thenReturn("topic1");
        when(t2.getTopic()).thenReturn("topic2");

        sensorService.deleteSensor(sensorId);
        verify(sensorRepository, times(1)).delete(sensor);
        verify(sensor, times(1)).getTopics();
        verify(ruleEngineAdapter, times(2)).deleteTopic(any());
    }

    @Test
    void getSensorErrors() {
        SensorErrorResponse sensorErrorResponse = mock(SensorErrorResponse.class);
        Page<SensorErrorResponse> sensorErrorLogs = mock(Page.class);
        when(sensorErrorLogRepository.findAllSensorErrorLogs(null)).thenReturn(sensorErrorLogs);
        when(sensorErrorLogs.getContent()).thenReturn(List.of(sensorErrorResponse));
        SensorErrorListResponse expected = new SensorErrorListResponse(List.of(sensorErrorResponse));

        SensorErrorListResponse actual = sensorService.getSensorErrors(null);
        assertEquals(expected.getErrors(), actual.getErrors());
        verify(sensorErrorLogRepository, times(1)).findAllSensorErrorLogs(null);
    }

    @Test
    void deleteSensorError_sensorErrorNotFound_throwError() {
        Integer sensorErrorId = 1;
        when(sensorErrorLogRepository.findById(sensorErrorId)).thenReturn(Optional.empty());

        assertThrows(SensorErrorLogNotFoundException.class,()->sensorService.deleteSensorError(sensorErrorId));
    }

    @Test
    void deleteSensorError() {
        Integer sensorErrorId = 1;
        SensorErrorLog sensorErrorLog = mock(SensorErrorLog.class);
        when(sensorErrorLogRepository.findById(sensorErrorId)).thenReturn(Optional.of(sensorErrorLog));

        sensorService.deleteSensorError(sensorErrorId);
        verify(sensorErrorLogRepository, times(1)).delete(sensorErrorLog);
    }

    @Test
    void getSensorTypes() {
        SensorTypeResponse sensorTypeResponse = mock(SensorTypeResponse.class);
        when(sensorTypeRepository.getAllSensorType()).thenReturn(List.of(sensorTypeResponse));

        SensorTypeListResponse actual = sensorService.getSensorTypes();
        assertEquals(List.of(sensorTypeResponse), actual.getSensorTypes());
        verify(sensorTypeRepository, times(1)).getAllSensorType();
    }

    @Test
    void addSensorError_sensorNotFound(CapturedOutput capturedOutput) {
        SensorErrorRequest request = mock(SensorErrorRequest.class);
        when(request.getSensorId()).thenReturn(1);
        when(sensorRepository.findById(request.getSensorId())).thenReturn(Optional.empty());

        sensorService.addSensorError(request);
        assertTrue(capturedOutput.getOut().contains("Sensor not found with id: 1"));
    }

    @Test
    void addSensorError_topicNotFound(CapturedOutput capturedOutput) {
        SensorErrorRequest request = mock(SensorErrorRequest.class);
        when(request.getSensorId()).thenReturn(1);
        when(sensorRepository.findById(request.getSensorId())).thenReturn(Optional.of(mock(Sensor.class)));
        when(topicRepository.findByTopicAndSensorSensorId(request.getTopic(), request.getSensorId())).thenReturn(Optional.empty());

        sensorService.addSensorError(request);
        assertTrue(capturedOutput.getOut().contains("Topic not found with topic: null and sensorId: 1"));
    }

    @Test
    void addSensorError() {
        SensorErrorRequest request = mock(SensorErrorRequest.class);
        Sensor sensor = mock(Sensor.class);
        Topic topic = mock(Topic.class);
        when(request.getSensorId()).thenReturn(1);
        when(request.getTopic()).thenReturn("topic");
        when(sensorRepository.findById(request.getSensorId())).thenReturn(Optional.of(sensor));
        when(topicRepository.findByTopicAndSensorSensorId(request.getTopic(), request.getSensorId())).thenReturn(Optional.of(topic));

        sensorService.addSensorError(request);
        verify(sensorErrorLogRepository, times(1)).save(any());
    }
}