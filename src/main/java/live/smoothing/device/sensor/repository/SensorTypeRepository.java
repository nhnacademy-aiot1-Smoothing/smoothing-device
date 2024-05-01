package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorTypeResponse;
import live.smoothing.device.sensor.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 센서 타입 레포지토리
 *
 * @author 우혜승
 */
public interface SensorTypeRepository extends JpaRepository<SensorType, String> {

    /**
     * 모든 센서 타입 조회
     *
     * @return 모든 센서 타입 목록
     */
    @Query("SELECT new live.smoothing.device.sensor.dto.SensorTypeResponse(s.sensorType) FROM SensorType s")
    List<SensorTypeResponse> getAllSensorType();
}
