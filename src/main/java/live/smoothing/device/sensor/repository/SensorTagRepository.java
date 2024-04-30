package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.entity.SensorTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorTagRepository extends JpaRepository<SensorTag, Integer>{
}
