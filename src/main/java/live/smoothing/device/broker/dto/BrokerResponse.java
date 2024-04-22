package live.smoothing.device.broker.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerResponse {

    private String ip;

    private Integer id;

    private Integer port;

    private String protocolType;

    private List<String> topics;
}
