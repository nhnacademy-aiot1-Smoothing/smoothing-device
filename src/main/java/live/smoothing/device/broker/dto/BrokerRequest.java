package live.smoothing.device.broker.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerRequest {

    private String brokerIp;
    private int brokerPort;
    private Integer brokerId;
    private String protocolType;
}
