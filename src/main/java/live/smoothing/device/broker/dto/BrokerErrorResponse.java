package live.smoothing.device.broker.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 브로커 에러 응답
 *
 * @author 우혜승
 */
@Getter
public class BrokerErrorResponse {

    private final String brokerErrorType;
    private final String brokerName;
    private final String createdAt;
    private final String solvedAt;

    /**
     * 브로커 에러 응답 생성자
     * @param brokerErrorType 브로커 에러 타입
     * @param brokerName 브로커 이름
     * @param createdAt 에러 발생 시간
     * @param solvedAt 에러 해결 시간
     */
    @Builder
    public BrokerErrorResponse(String brokerErrorType, String brokerName, LocalDateTime createdAt, LocalDateTime solvedAt) {
        this.brokerErrorType = brokerErrorType;
        this.brokerName = brokerName;
        this.createdAt = createdAt == null ? null : createdAt.toString();
        this.solvedAt = solvedAt == null ? null : solvedAt.toString();
    }
}
