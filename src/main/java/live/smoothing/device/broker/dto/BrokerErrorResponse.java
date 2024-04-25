package live.smoothing.device.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BrokerErrorResponse {

    private final String brokerErrorType;
    private final String brokerName;
    private final LocalDateTime createdAt;
    private final LocalDateTime solvedAt;
}
