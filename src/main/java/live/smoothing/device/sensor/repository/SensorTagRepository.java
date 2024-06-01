package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.entity.SensorTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 센서 태그 레포지토리
 *
 * @author 우혜승
 */
public interface SensorTagRepository extends JpaRepository<SensorTag, Integer>{

    boolean existsByTagTagIdAndSensorSensorId(Integer tagId, Integer sensorId);

    SensorTag findBySensorSensorIdAndTagTagId(Integer sensorId, Integer tagId);
}
