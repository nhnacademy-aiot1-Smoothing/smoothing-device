package live.smoothing.device.mq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BrokerErrorRequest {

    private Integer brokerId;
    private String brokerErrorType;
    private LocalDateTime createdAt;
}
