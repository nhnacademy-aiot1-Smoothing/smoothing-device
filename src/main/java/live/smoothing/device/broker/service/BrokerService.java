package live.smoothing.device.broker.service;

import live.smoothing.device.broker.dto.BrokerGenerateRequest;
import live.smoothing.device.broker.entity.Broker;

import java.util.List;

public interface BrokerService {

    Broker addBroker(BrokerGenerateRequest request);
    String getBrokerName(Integer brokerId);
    List<Broker> getBrokers();
}
