package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.entity.ProtocolType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocolTypeRepository extends JpaRepository<ProtocolType, String> {
}
