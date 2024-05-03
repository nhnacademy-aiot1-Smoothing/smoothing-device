package live.smoothing.device.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 브로커 응답
 *
 * @author 우혜승
 */
@Getter
@Builder
@AllArgsConstructor
public class BrokerResponse {

    private final Integer brokerId;
    private final String brokerIp;
    private final Integer brokerPort;
    private final String brokerName;
    private final String protocolType;
}
