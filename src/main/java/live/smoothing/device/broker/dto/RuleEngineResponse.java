package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

/**
 * Rule Engine 초기화용 응답
 *
 * @author 우혜승
 */
@Builder
@AllArgsConstructor
public class RuleEngineResponse {

    @JsonProperty("brokerId")
    private Integer brokerId;
    @JsonProperty("brokerIp")
    private String brokerIp;
    @JsonProperty("brokerPort")
    private Integer brokerPort;
    @JsonProperty("protocolType")
    private String protocolType;
    @JsonProperty("topics")
    private Set<String> topics;
}
