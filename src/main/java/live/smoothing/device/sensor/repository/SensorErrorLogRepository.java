package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorErrorResponse;
import live.smoothing.device.sensor.entity.SensorErrorLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 센서 에러 로그 레포지토리
 *
 * @author 우혜승
 */
public interface SensorErrorLogRepository extends JpaRepository<SensorErrorLog, Integer>{

    /**
     * 센서 에러 로그 전체 조회
     *
     * @param pageable 페이징 정보
     * @return 센서 에러 로그 전체 조회
     */
    @Query("SELECT new live.smoothing.device.sensor.dto.SensorErrorResponse(s.sensorErrorLogId ,s.sensorErrorType, s.sensor.sensorName, s.sensorErrorCreatedAt, s.sensorErrorValue, s.topic.topic) FROM SensorErrorLog s")
    Page<SensorErrorResponse> findAllSensorErrorLogs(Pageable pageable);
}
