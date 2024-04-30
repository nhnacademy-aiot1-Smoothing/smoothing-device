package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerResponse;
import live.smoothing.device.broker.entity.Broker;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrokerRepository extends JpaRepository<Broker, Integer> {

    boolean existsByBrokerIpOrBrokerName(String ip, String name);

    @EntityGraph(attributePaths = {"sensors.topics"})
    @Query("SELECT b FROM Broker b LEFT OUTER JOIN Sensor s ON b.brokerId = s.broker.brokerId LEFT OUTER JOIN Topic t ON s.sensorId = t.sensor.sensorId")
    List<Broker> getAllWith();

    @Query("SELECT new live.smoothing.device.broker.dto.BrokerResponse(b.brokerId, b.brokerIp, b.brokerPort, b.brokerName, b.protocolType.protocolType) FROM Broker b")
    List<BrokerResponse> getBrokers();

}
