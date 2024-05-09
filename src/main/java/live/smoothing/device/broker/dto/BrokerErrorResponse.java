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

    private final Integer brokerErrorId;
    private final String brokerErrorType;
    private final String brokerName;
    private final String createdAt;
    private final String solvedAt;
    private final String brokerIp;
    private final Integer brokerPort;

    /**
     * 브로커 에러 응답 생성자
     * @param brokerErrorId 브로커 에러 아이디
     * @param brokerErrorType 브로커 에러 타입
     * @param brokerName 브로커 이름
     * @param createdAt 에러 발생 시간
     * @param solvedAt 에러 해결 시간
     * @param brokerIp 브로커 아이피
     * @param brokerPort 브로커 포트
     */
    @Builder
    public BrokerErrorResponse(Integer brokerErrorId ,String brokerErrorType, String brokerName, LocalDateTime createdAt, LocalDateTime solvedAt, String brokerIp, Integer brokerPort) {
        this.brokerErrorId = brokerErrorId;
        this.brokerErrorType = brokerErrorType;
        this.brokerName = brokerName;
        this.createdAt = createdAt == null ? null : createdAt.toString();
        this.solvedAt = solvedAt == null ? null : solvedAt.toString();
        this.brokerIp = brokerIp;
        this.brokerPort = brokerPort;
    }
}
