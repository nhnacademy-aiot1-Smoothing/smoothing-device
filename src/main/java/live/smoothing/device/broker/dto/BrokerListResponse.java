package live.smoothing.device.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 브로커 목록 응답
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class BrokerListResponse {

    private final List<BrokerResponse> brokers;
}
