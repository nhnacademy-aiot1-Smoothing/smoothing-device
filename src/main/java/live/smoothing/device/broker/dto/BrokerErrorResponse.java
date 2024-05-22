package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 브로커 에러 응답
 *
 * @author 우혜승
 */
public class BrokerErrorResponse {

    @JsonProperty("brokerErrorId")
    private final Integer brokerErrorId;
    @JsonProperty("brokerErrorType")
    private final String brokerErrorType;
    @JsonProperty("brokerName")
    private final String brokerName;
    @JsonProperty("createdAt")
    private final String createdAt;
    @JsonProperty("solvedAt")
    private final String solvedAt;
    @JsonProperty("brokerIp")
    private final String brokerIp;
    @JsonProperty("brokerPort")
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
