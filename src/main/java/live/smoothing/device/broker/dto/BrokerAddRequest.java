package live.smoothing.device.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 브로커 추가 요청
 *
 * @author 우혜승
 */
@Getter
public class BrokerAddRequest {

    private String brokerIp;
    private Integer brokerPort;
    private String brokerName;
    private String protocolType;

}
