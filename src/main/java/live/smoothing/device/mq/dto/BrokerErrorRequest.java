package live.smoothing.device.mq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 브로커 에러 추가 요청 클래스
 *
 * @author 우혜승
 */
@Getter
@NoArgsConstructor
public class BrokerErrorRequest {

    private Integer brokerId;
    private String brokerErrorType;
    private String createdAt;
}
