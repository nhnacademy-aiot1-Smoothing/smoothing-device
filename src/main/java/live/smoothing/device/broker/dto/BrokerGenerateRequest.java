package live.smoothing.device.broker.dto;

import lombok.*;

/**
 * 브로커 생성 요청
 *
 * @author 우혜승
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerGenerateRequest {

    private String brokerIp;
    private int brokerPort;
    private Integer brokerId;
    private String protocolType;
}
