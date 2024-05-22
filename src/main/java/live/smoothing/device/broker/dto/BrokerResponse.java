package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 브로커 응답
 *
 * @author 우혜승
 */
@Builder
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class BrokerResponse {

    private final Integer brokerId;
    private final String brokerIp;
    private final Integer brokerPort;
    private final String brokerName;
    private final String protocolType;
}
