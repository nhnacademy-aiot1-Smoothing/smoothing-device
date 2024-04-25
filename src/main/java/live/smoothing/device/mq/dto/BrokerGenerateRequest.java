package live.smoothing.device.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrokerGenerateRequest {

    private String brokerIp;
    private int brokerPort;
    private Integer brokerId;
    private String protocolType;
}
