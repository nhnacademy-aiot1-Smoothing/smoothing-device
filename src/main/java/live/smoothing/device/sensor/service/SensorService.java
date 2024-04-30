package live.smoothing.device.sensor.service;

import live.smoothing.device.broker.dto.BrokerListResponse;
import live.smoothing.device.mq.dto.SensorErrorRequest;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.entity.Sensor;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SensorService {

    void saveSensor(SensorRegisterRequest sensorRegisterRequest);

    SensorListResponse getSensors(Integer brokerId, Pageable pageable);

    void updateSensor(Integer sensorId, SensorUpdateRequest sensorUpdateRequest);

    void deleteSensor(Integer sensorId);

    SensorErrorListResponse getSensorErrors(Pageable pageable);

    void deleteSensorError(Integer sensorErrorId);

    SensorTypeListResponse getSensorTypes();

    void addSensorError(SensorErrorRequest request);
}