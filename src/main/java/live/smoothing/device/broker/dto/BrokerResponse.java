package live.smoothing.device.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrokerResponse {

    private final Integer brokerId;
    private final String brokerIp;
    private final Integer brokerPort;
    private final String brokerName;
    private final String protocolType;
}
