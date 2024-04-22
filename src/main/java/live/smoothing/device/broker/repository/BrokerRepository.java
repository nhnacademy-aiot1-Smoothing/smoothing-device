package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.entity.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerRepository extends JpaRepository<Broker, Integer> {

    boolean existsByBrokerIpOrBrokerName(String ip, String name);

}
