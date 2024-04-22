package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.sensor.dto.SensorRegisterRequest;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.repository.SensorRepository;
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
    private final BrokerRepository brokerRepository;

    @Override
    public boolean isRegistered(String sensorName) {

        return sensorRepository
                .findBySensorName(sensorName)
                .isPresent();
    }

    @Override
    public List<Sensor> getSensors(Integer brokerId) {

        return sensorRepository.findByBrokerBrokerId(brokerId);
    }

    @Override
    @Transactional
    public Sensor saveSensor(SensorRegisterRequest request) {

        sensorRepository.findBySensorName(request.getSensorName())
                .ifPresent(sensor -> {
                    throw new IllegalArgumentException("이미 등록된 센서입니다.");
                });

        Sensor sensor = Sensor.builder()
                .sensorName(request.getSensorName())
                .broker(brokerRepository.getReferenceById(request.getBrokerId()))
                .sensorRegisteredAt(LocalDateTime.now())
                .sensorType(request.getSensorType())
                .build();
        return sensorRepository.save(sensor);
    }
}