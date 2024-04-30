package live.smoothing.device.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 브로커 에러 목록 응답
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class BrokerErrorListResponse {

    private final List<BrokerErrorResponse> connectErrors;
}
