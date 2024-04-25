package live.smoothing.device.broker.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerInitResponse {

    private Integer brokerId;

    private String brokerIp;

    private Integer brokerPort;

    private String protocolType;

    private List<String> topics;
}
