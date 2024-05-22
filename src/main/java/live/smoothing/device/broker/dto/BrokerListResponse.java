package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 브로커 목록 응답
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class BrokerListResponse {

    @JsonProperty("brokers")
    private final List<BrokerResponse> brokers;
    @JsonProperty("totalPage")
    private final int totalPage;
}
