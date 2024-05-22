package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 브로커 생성 요청
 *
 * @author 우혜승
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerGenerateRequest {

    @JsonProperty("brokerIp")
    private String brokerIp;
    @JsonProperty("brokerPort")
    private int brokerPort;
    @JsonProperty("brokerId")
    private Integer brokerId;
    @JsonProperty("protocolType")
    private String protocolType;
}
