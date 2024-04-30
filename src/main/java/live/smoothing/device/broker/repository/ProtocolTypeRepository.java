package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.entity.ProtocolType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 프로토콜 타입 레포지토리
 *
 * @author 우혜승
 */
public interface ProtocolTypeRepository extends JpaRepository<ProtocolType, String> {
}
