package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 브로커 응답
 *
 * @author 우혜승
 */
@Builder
@AllArgsConstructor
public class BrokerResponse {

    @JsonProperty("brokerId")
    private final Integer brokerId;
    @JsonProperty("brokerIp")
    private final String brokerIp;
    @JsonProperty("brokerPort")
    private final Integer brokerPort;
    @JsonProperty("brokerName")
    private final String brokerName;
    @JsonProperty("protocolType")
    private final String protocolType;
}
