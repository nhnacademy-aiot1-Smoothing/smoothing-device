package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorResponse;
import live.smoothing.device.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    List<SensorResponse> findByBrokerBrokerId(Integer brokerId);

    @EntityGraph(attributePaths = {"topics"})
    Optional<Sensor> findSensorWithTopicBySensorId(Integer sensorId);
}