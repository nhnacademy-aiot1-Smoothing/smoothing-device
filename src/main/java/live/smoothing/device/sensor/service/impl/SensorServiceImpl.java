package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.broker.dto.BrokerListResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.exception.BrokerNotFoundException;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorErrorLog;
import live.smoothing.device.sensor.entity.SensorType;
import live.smoothing.device.sensor.exception.SensorErrorLogNotFoundException;
import live.smoothing.device.sensor.exception.SensorNotFoundException;
import live.smoothing.device.sensor.exception.SensorTypeNoutFoundException;
import live.smoothing.device.sensor.repository.SensorErrorLogRepository;
import live.smoothing.device.sensor.repository.SensorRepository;
import live.smoothing.device.sensor.repository.SensorTypeRepository;
import live.smoothing.device.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final BrokerRepository brokerRepository;
    private final SensorErrorLogRepository sensorErrorLogRepository;

    @Override
    @Transactional
    public void saveSensor(SensorRegisterRequest sensorRegisterRequest) {
        SensorType sensorType = sensorTypeRepository.findById(sensorRegisterRequest.getSensorType())
                .orElseThrow(SensorTypeNoutFoundException::new);
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

    @Override
    public SensorListResponse getSensors(Integer brokerId) {
        if(!brokerRepository.existsById(brokerId)) {
            throw new BrokerNotFoundException();
        }
        List<SensorResponse> sensorResponses = sensorRepository.findByBrokerBrokerId(brokerId);
        return new SensorListResponse(sensorResponses);
    }

    @Override
    @Transactional
    public void updateSensor(Integer sensorId, SensorUpdateRequest sensorUpdateRequest) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(SensorNotFoundException::new);
        SensorType sensorType = sensorTypeRepository.findById(sensorUpdateRequest.getSensorType())
                .orElseThrow(SensorTypeNoutFoundException::new);

        sensor.updateSensorName(sensorUpdateRequest.getSensorName());
        sensor.updateSensorType(sensorType);

        sensorRepository.save(sensor);
    }

    @Override
    public void deleteSensor(Integer sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(SensorNotFoundException::new);
        //todo mq
        sensorRepository.delete(sensor);
    }

    @Override
    public SensorErrorListResponse getSensorErrors() {
        return new SensorErrorListResponse(sensorErrorLogRepository.findAllSensorErrorLogs());
    }

    @Override
    public void deleteSensorError(Integer sensorErrorId) {
        SensorErrorLog sensorErrorLog = sensorErrorLogRepository.findById(sensorErrorId)
                .orElseThrow(SensorErrorLogNotFoundException::new);
        sensorErrorLogRepository.delete(sensorErrorLog);
    }

    @Override
    public SensorTypeListResponse getSensorTypes() {
        return new SensorTypeListResponse(sensorTypeRepository.getAllSensorType());
    }

}