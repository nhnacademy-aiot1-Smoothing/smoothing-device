package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProtocolTypeResponse {

    @JsonProperty("protocolTypes")
    private List<String> protocolTypes;
}
