package live.smoothing.device.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrokerUpdateRequest {

    private String brokerIp;
    private Integer brokerPort;
    private String brokerName;
    private String protocolType;
}
