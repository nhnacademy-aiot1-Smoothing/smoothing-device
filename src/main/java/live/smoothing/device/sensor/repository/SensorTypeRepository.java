package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorTypeResponse;
import live.smoothing.device.sensor.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorTypeRepository extends JpaRepository<SensorType, String> {

    @Query("SELECT new live.smoothing.device.sensor.dto.SensorTypeResponse(s.sensorType) FROM SensorType s")
    List<SensorTypeResponse> getAllSensorType();
}
