package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.adapter.RuleEngineAdapter;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.exception.BrokerNotFoundException;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.dto.TopicRequest;
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
import live.smoothing.device.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * SensorService 구현체<br>
 * 센서와 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
@Slf4j
@RequiredArgsConstructor
@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final BrokerRepository brokerRepository;
    private final SensorErrorLogRepository sensorErrorLogRepository;
    private final TopicRepository topicRepository;
    private final RuleEngineAdapter ruleEngineAdapter;

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void saveSensor(SensorRegisterRequest sensorRegisterRequest) {
        SensorType sensorType = sensorTypeRepository.findById(sensorRegisterRequest.getSensorType())
                .orElseThrow(SensorTypeNotFoundException::new);
        Broker broker = brokerRepository.findById(sensorRegisterRequest.getBrokerId())
                .orElseThrow(BrokerNotFoundException::new);

        Sensor sensor = Sensor.builder()
                .sensorName(sensorRegisterRequest.getSensorName())
                .sensorType(sensorTypeRepository.getReferenceById(sensorRegisterRequest.getSensorType()))
                .sensorRegisteredAt(LocalDateTime.now())
                .sensorType(sensorType)
                .broker(broker)
                .build();
        sensorRepository.save(sensor);
    }

    /**
     * @inheritDoc
     */
    @Override
    public SensorListResponse getSensors(Integer brokerId, Pageable pageable) {
        if(!brokerRepository.existsById(brokerId)) {
            throw new BrokerNotFoundException();
        }
        Page<SensorResponse> sensorResponses = sensorRepository.findByBrokerBrokerId(brokerId, pageable);
        return new SensorListResponse(sensorResponses.getContent(), sensorResponses.getTotalPages());
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void updateSensor(Integer sensorId, SensorUpdateRequest sensorUpdateRequest) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(SensorNotFoundException::new);
        SensorType sensorType = sensorTypeRepository.findById(sensorUpdateRequest.getSensorType())
                .orElseThrow(SensorTypeNotFoundException::new);

        sensor.updateSensorName(sensorUpdateRequest.getSensorName());
        sensor.updateSensorType(sensorType);

        sensorRepository.save(sensor);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteSensor(Integer sensorId) {
        Sensor sensor = sensorRepository.findSensorWithTopicBySensorId(sensorId)
                .orElseThrow(SensorNotFoundException::new);
        sensorRepository.delete(sensor);

        for(Topic t : sensor.getTopics()) {
            ruleEngineAdapter.deleteTopic(new TopicRequest(sensor.getBroker().getBrokerId(), t.getTopic()));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public SensorErrorListResponse getSensorErrors(Pageable pageable) {
        Page<SensorErrorResponse> sensorErrorResponses = sensorErrorLogRepository.findAllSensorErrorLogs(pageable);
        return new SensorErrorListResponse(sensorErrorResponses.getContent());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteSensorError(Integer sensorErrorId) {
        SensorErrorLog sensorErrorLog = sensorErrorLogRepository.findById(sensorErrorId)
                .orElseThrow(SensorErrorLogNotFoundException::new);
        sensorErrorLogRepository.delete(sensorErrorLog);
    }

    /**
     * @inheritDoc
     */
    @Override
    public SensorTypeListResponse getSensorTypes() {
        return new SensorTypeListResponse(sensorTypeRepository.getAllSensorType());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addSensorError(SensorErrorRequest request) {
        Optional<Sensor> sensor = sensorRepository.findById(request.getSensorId());
        if(sensor.isEmpty()) {
            log.error("Sensor not found with id: {}", request.getSensorId());
            return;
        }

        Optional<Topic> topic = topicRepository.findByTopicAndSensorSensorId(request.getTopic(), request.getSensorId());
        if(topic.isEmpty()) {
            log.error("Topic not found with topic: {} and sensorId: {}", request.getTopic(), request.getSensorId());
            return;
        }

        SensorErrorLog sensorErrorLog = SensorErrorLog.builder()
                .sensor(sensor.get())
                .sensorErrorType(request.getSensorErrorType())
                .sensorErrorValue(request.getSensorValue())
                .topic(topic.get())
                .sensorErrorCreatedAt(request.getCreatedAt())
                .build();
        sensorErrorLogRepository.save(sensorErrorLog);
    }

}