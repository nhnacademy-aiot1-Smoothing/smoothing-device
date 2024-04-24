package live.smoothing.device.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BrokerListResponse {

    private final List<BrokerResponse> brokers;
}
