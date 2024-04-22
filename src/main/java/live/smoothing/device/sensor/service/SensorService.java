package live.smoothing.device.sensor.service;

import live.smoothing.device.sensor.dto.SensorRegisterRequest;
import live.smoothing.device.sensor.entity.Sensor;

import java.util.List;

public interface SensorService {

    boolean isRegistered(String sensorName);

    List<Sensor> getSensors(Integer brokerId);

    Sensor saveSensor(SensorRegisterRequest request);
}