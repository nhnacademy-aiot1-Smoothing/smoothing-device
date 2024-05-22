package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Set;

/**
 * Rule Engine 초기화용 응답
 *
 * @author 우혜승
 */
@Builder
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class RuleEngineResponse {

    private Integer brokerId;
    private String brokerIp;
    private Integer brokerPort;
    private String protocolType;
    private Set<String> topics;
}
