package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorErrorResponse;
import live.smoothing.device.sensor.entity.SensorErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorErrorLogRepository extends JpaRepository<SensorErrorLog, Integer>{

    @Query("SELECT new live.smoothing.device.sensor.dto.SensorErrorResponse(s.sensorErrorType, s.sensor.sensorName, s.sensorErrorCreatedAt, s.sensorErrorValue, s.topic.topic) FROM SensorErrorLog s")
    List<SensorErrorResponse> findAllSensorErrorLogs();
}
