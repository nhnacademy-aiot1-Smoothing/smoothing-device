package live.smoothing.device.broker.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleEngineResponse {

    private Integer brokerId;

    private String brokerIp;

    private Integer brokerPort;

    private String protocolType;

    private Set<String> topics;
}
