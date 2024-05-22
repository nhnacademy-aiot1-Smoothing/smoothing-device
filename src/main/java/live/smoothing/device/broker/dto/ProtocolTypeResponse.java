package live.smoothing.device.broker.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ProtocolTypeResponse {

    private List<String> protocolTypes;
}
