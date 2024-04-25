package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorResponse;
import live.smoothing.device.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    List<SensorResponse> findByBrokerBrokerId(Integer brokerId);
}