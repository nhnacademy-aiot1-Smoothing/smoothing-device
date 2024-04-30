package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerResponse;
import live.smoothing.device.broker.entity.Broker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 브로커 레포지토리
 *
 * @author 우혜승
 */
public interface BrokerRepository extends JpaRepository<Broker, Integer> {

    /**
     * 하위 엔티티를 포함한 모든 브로커 조회
     *
     * @return 브로커 리스트
     */
    @EntityGraph(attributePaths = {"sensors.topics"})
    @Query("SELECT b FROM Broker b " +
            "LEFT OUTER JOIN Sensor s ON b.brokerId = s.broker.brokerId " +
            "LEFT OUTER JOIN Topic t ON s.sensorId = t.sensor.sensorId")
    List<Broker> getAllWith();

    /**
     * 브로커 페이지 조회
     *
     * @param pageable 페이징 정보
     * @return 브로커 페이지
     */
    @Query("SELECT new live.smoothing.device.broker.dto.BrokerResponse(b.brokerId, b.brokerIp, b.brokerPort, b.brokerName, b.protocolType.protocolType) FROM Broker b ")
    Page<BrokerResponse> getBrokers(Pageable pageable);

}
