package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorResponse;
import live.smoothing.device.sensor.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 센서 레포지토리
 *
 * @author 우혜승
 */
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    /**
     * 브로커 아이디로 센서 조회
     *
     * @param brokerId 브로커 아이디
     * @param pageable 페이징 정보
     * @return 브로커 아이디로 센서 조회
     */
    Page<SensorResponse> findByBrokerBrokerId(Integer brokerId, Pageable pageable);

    /**
     * 센서 아이디로 센서 및 하위 토픽들 JOIN 조회
     *
     * @param sensorId 센서 아이디
     * @return 센서 아이디로 조회된 센서
     */
    @EntityGraph(attributePaths = {"topics"})
    Optional<Sensor> findSensorWithTopicBySensorId(Integer sensorId);
}