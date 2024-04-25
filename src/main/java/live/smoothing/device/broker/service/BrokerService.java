package live.smoothing.device.broker.service;

import live.smoothing.device.broker.dto.*;
import live.smoothing.device.mq.dto.BrokerErrorRequest;

import java.util.List;

public interface BrokerService {

    List<BrokerInitResponse> getInitBrokers();

    void addBroker(BrokerAddRequest request);

    BrokerListResponse getBrokers();

    void updateBroker(Integer brokerId, BrokerUpdateRequest brokerUpdateRequest);

    void deleteBroker(Integer brokerId);

    BrokerErrorListResponse getErrors();

    void deleteError(Integer brokerErrorId);

    void addBrokerError(BrokerErrorRequest request);

}
