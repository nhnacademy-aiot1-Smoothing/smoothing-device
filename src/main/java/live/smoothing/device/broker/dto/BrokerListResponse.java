package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 브로커 목록 응답
 *
 * @author 우혜승
 */
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class BrokerListResponse {

    private final List<BrokerResponse> brokers;
    private final int totalPage;
}
